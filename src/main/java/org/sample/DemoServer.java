package org.sample;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author liudong17
 * @date 2018/11/21
 */
@Slf4j
public class DemoServer extends Thread {
    private ServerSocket serverSocket;

    private Executor executor;

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            executor = Executors.newFixedThreadPool(8);
            while (true) {
                log.info("running");
                Socket socket = serverSocket.accept();
                executor.execute(new RequestHandler(socket));
            }
        } catch (IOException e) {
            log.error("exception", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    log.error("exception", e);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DemoServer server = new DemoServer();
        log.info("start");
        server.start();
        Thread.sleep(1000);
        log.info("port:{}", server.getPort());
        for (int i = 0; i < 1; i++) {
            try (Socket client = new Socket(InetAddress.getLocalHost(), server.getPort())) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                bufferedReader.lines().forEach(log::info);
            }
        }
        System.exit(1);
    }

    /**
     * 简化实现，不做读取，直接发送字符串
     */
    class RequestHandler extends Thread {
        private Socket socket;

        RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                out.println("Hello world!" + Thread.currentThread().getName());
                out.flush();
            } catch (Exception e) {
                log.error("exception", e);
            }
        }
    }

    static class NioServer extends Thread {
        @Override
        public void run() {
            try (Selector selector = Selector.open();
                 ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
                serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8888));
                serverSocket.configureBlocking(false);
                // 注册到 Selector，并说明关注点
                serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    selector.select();// 阻塞等待就绪的 Channel，这是关键点之一
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        // 生产系统中一般会额外进行就绪状态检查
                        sayHelloWorld((ServerSocketChannel) key.channel());
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sayHelloWorld(ServerSocketChannel server) throws IOException {
            try (SocketChannel client = server.accept()) {
                client.write(Charset.defaultCharset().encode("Hello world!"));
            }
        }
    }
}
