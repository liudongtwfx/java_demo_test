package org.sample.jdk.net;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author liudong17
 * @date 2019-05-30 16:58
 */
@Slf4j
public class NioServer implements Server, Runnable, Closeable {

    private final int port;
    private ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocateDirect(1024);

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    NioServer(int port) {
        this.port = port;
        init();
    }

    private void init() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            log.info("listening on port " + port);

            this.selector = Selector.open();

            // 绑定channel的accept
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            log.error("exception", e);
        }
    }

    @Override
    public void run() {
        // block api
        try {
            while (selector.select() > 0) {
                serve();
            }
        } catch (IOException e) {
            log.error("e", e);
            close();
        }
    }

    @Override
    public void close() {
        try {
            serverSocketChannel.close();
            selector.close();
        } catch (IOException ioe) {
            log.error("close exception", ioe);
        }
    }

    @Override
    public void serve() {
        try {
            doServe();
        } catch (Exception e) {
            log.error("serve exception", e);
        }
    }

    private void doServe() throws Exception {
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            // 新连接
            if (selectionKey.isAcceptable()) {
                accept(selectionKey);
            }
            // 服务端关心的可读，意味着有数据从client传来了，根据不同的需要进行读取，然后返回
            else if (selectionKey.isReadable()) {
                read(selectionKey);
            }

            // 实际上服务端不在意这个，这个写入应该是client端关心的，这只是个demo,顺便试一下selectionKey的attach方法
            else if (selectionKey.isWritable()) {
                write(selectionKey);
            }
        }
    }

    private void write(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        String message = (String) selectionKey.attachment();
        if (StringUtils.isBlank(message)) {
            return;
        }
        selectionKey.attach(null);

        writeBuffer.clear();
        writeBuffer.put((message.trim() + " : haha").getBytes());
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }
    }

    private void accept(SelectionKey selectionKey) throws Exception {
        log.info("isAcceptable");
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();

        // 新注册channel
        SocketChannel socketChannel = server.accept();
        if (socketChannel == null) {
            return;
        }
        socketChannel.configureBlocking(false);
        // 注意！这里和阻塞io的区别非常大，在编码层面之前的等待输入已经变成了注册事件，这样我们就可以在等待的时候做别的事情，
        // 比如监听更多的socket连接，也就是之前说了一个线程监听多个socket连接。这也是在编码的时候最直观的感受
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);


        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        buffer.put("hi new channel".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    private void read(SelectionKey selectionKey) throws Exception {
        log.info("isReadable");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        readBuffer.clear();
        socketChannel.read(readBuffer);
        readBuffer.flip();

        String receiveData = Charset.forName("UTF-8").decode(readBuffer).toString();
        log.info("receiveData:" + receiveData);

        // 把读到的数据绑定到key中
        selectionKey.attach("server message echo:" + receiveData);
    }
}
