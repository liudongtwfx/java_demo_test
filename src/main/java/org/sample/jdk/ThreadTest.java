package org.sample.jdk;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;


/**
 * @author liudong17
 * @date 2019-01-25 10:37
 */
public class ThreadTest {

    private static final BasicThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern("workerthread-%d")
            .daemon(false)
            .priority(Thread.MAX_PRIORITY)
            .build();

    public static void main(String[] args) throws Exception {
        Thread.getAllStackTraces().keySet().forEach(System.out::println);
        new Thread(new MyRunner()).start();
    }

    static class MyRunner implements Runnable {
        private static int num = 1;

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("--------");
                Thread.getAllStackTraces().keySet().forEach(System.out::println);
            } catch (Exception e) {

            }
        }
    }
}
