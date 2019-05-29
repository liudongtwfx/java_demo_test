package org.sample.jdk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liudong17
 * @date 2019-05-24 11:11
 */
@Slf4j
public class StreamDemoTest {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 20000; i++) {
            doWithClose();
        }
        System.out.println("time gap:" + stopWatch.getTime(TimeUnit.MILLISECONDS));
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 20000; i++) {
            doWithNoClose();
        }
        System.out.println("time gap:" + stopWatch.getTime(TimeUnit.MILLISECONDS));

    }

    private static void doWithNoClose() {
        try {
            FileInputStream inputStream = new FileInputStream(new ClassPathResource("log4j.properties").getFile());
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
        } catch (IOException e) {
            //log.error("", e);
        }
    }


    private static void doWithClose() {
        try (FileInputStream inputStream = new FileInputStream(new ClassPathResource("log4j.properties").getFile())) {
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
        } catch (Exception e) {
            log.error("exception", e);
        }
    }
}
