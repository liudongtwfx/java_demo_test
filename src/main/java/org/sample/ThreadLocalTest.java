package org.sample;

/**
 * @author liudong17
 * @date 2019-01-23 16:41
 */
public class ThreadLocalTest {
    private static final ThreadLocal<String> THREAD_NAME = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new MyThread()).start();
        }
    }

    static class MyThread implements Runnable {

        @Override
        public void run() {
            THREAD_NAME.set(Thread.currentThread().getName());
        }
    }
}
