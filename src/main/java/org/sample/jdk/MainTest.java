package org.sample.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liudong17
 * @date 2019-05-10 11:47
 */
@Slf4j
public class MainTest {
    public static void main(String[] args) {
        process();
    }

    private static void process() {
        Class<?> t = int.class;
        System.out.println(t);
    }

    public String fun(String s) {
        return "%" + s + "%";
    }

    public String string(String s) {
        return new StringBuilder().append("%").append(s).append("%").toString();
    }
}
