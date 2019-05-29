package org.sample.jdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author liudong17
 * @date 2018-11-27
 */
public class InvokeDynamicTest {

    private int n;

    private String str;

    public int getN() {
        return n;
    }

    public StringBuilder getStr() {
        StringBuilder sb = new StringBuilder();
        return sb;
    }

    public synchronized void fun(String... args) {
        synchronized (this) {

        }
    }

    public <T extends Number> List<T> getRandom(T t) {
        return Collections.singletonList(t);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            InvokeDynamicTest test = new InvokeDynamicTest();
            test.getN();
        }
        try (InputStream stream = new FileInputStream(new File(""))) {

        } catch (IOException e) {

        }
    }
}
