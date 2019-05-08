package org.sample.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liudong17
 * @date 2019-04-08 16:22
 */
@Slf4j
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(20));
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(new MyRunnable(i));
        }
    }

    static class MyRunnable implements Runnable {
        private int threadNo;

        MyRunnable(int threadNo) {
            this.threadNo = threadNo;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Thread No:{}", threadNo);
        }
    }
}
