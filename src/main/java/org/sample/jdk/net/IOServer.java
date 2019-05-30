package org.sample.jdk.net;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liudong17
 * @date 2019-05-30 16:39
 */
@Slf4j
public class IOServer implements Server, Runnable, Closeable {
    private final int port;
    private ServerSocket serverSocket;

    IOServer(int port) {
        this.port = port;
        init();
    }

    @Override
    public void run() {
        while (true) {
            try {
                serve();
            } catch (Exception e) {
                log.error("serve exception", e);
            }
        }
    }

    @Override
    public void serve() {
        try {
            doServe();
        } catch (Exception e) {
            log.error("serve exception", e);
            throw new RuntimeException("serve exception:" + e.getMessage());
        }
    }

    private void doServe() throws Exception {
        Socket socket = this.serverSocket.accept();
        byte[] bytes = new byte[1024];
        try (InputStream inputStream = socket.getInputStream()) {
            int readLength = inputStream.read(bytes);
            log.info("content:{},length:{}", new String(bytes).trim(), readLength);
        }
    }

    private void init() {
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException ioe) {
            log.error("exception", ioe);
            throw new RuntimeException("init failed:" + ioe.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
