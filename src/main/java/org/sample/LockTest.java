package org.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liudong17
 * @date 2018-11-22
 */
@Slf4j
public class LockTest {
    private static final Map<String, String> m = new TreeMap<>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();

    private static final Set<Integer> read = new HashSet<>();


    public String get(String key) {
        //r.lock();
        try {
            return m.get(key);
        } finally {
            //r.unlock();
        }
    }

    public String put(String key, String value) {
        w.lock();
        try {
            Thread.sleep(100);
            return m.put(key, value);
        } catch (InterruptedException e) {
            return "";
        } finally {
            w.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(100);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                executor.execute(new ReadRunner(String.valueOf(i - 1)));
            } else {
                executor.execute(new WriteRunner(String.valueOf(i), String.valueOf(i)));
            }
        }
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("cost {}", stopWatch.getTime());
        System.out.println(read.size());
        System.exit(1);
        new Thread().join();
    }


    static class ReadRunner implements Runnable {
        private String key;

        ReadRunner(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            String value = new LockTest().get(key);
            if (StringUtils.isNotBlank(value)) {
                log.info("key is:{},value is:{}", key, new LockTest().get(key));
                read.add(Integer.parseInt(key));
            }
        }
    }


    static class WriteRunner implements Runnable {
        private String key;
        private String value;

        WriteRunner(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            new LockTest().put(key, value);
        }
    }
}
