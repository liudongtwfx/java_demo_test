package org.sample.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liudong17
 * @date 2019-05-30 16:59
 */
@Slf4j
public class LogUtils {
    private LogUtils() {
    }

    public static <T> T log(T t) {
        log.info("{}", t);
        return t;
    }
}
