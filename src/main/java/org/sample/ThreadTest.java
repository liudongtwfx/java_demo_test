package org.sample;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liudong17
 * @date 2019-01-25 10:37
 */
public class ThreadTest {

    private static final BasicThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern("workerthread-%d")
            .daemon(false)
            .priority(Thread.MAX_PRIORITY)
            .build();

    public static void main(String[] args) {
        MainThread mainThread = new MainThread();
        Thread thread = new Thread(mainThread);
        thread.setDaemon(true);
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2, FACTORY);
        scheduledExecutorService.scheduleAtFixedRate(new MyRunner(), 1, 2, TimeUnit.SECONDS);
        thread.start();
    }

    static class MyRunner implements Runnable {
        private static int num = 1;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " hello world!" + num);
            ++num;
        }
    }

    static class MainThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
