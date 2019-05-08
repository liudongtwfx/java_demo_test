package org.sample;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author liudong17
 * @date 2019-01-23 16:19
 */
public class EnumTest {
    public static void main(String[] args) {
        Class<Test> declaringClass = Test.ONE.getDeclaringClass();
        System.out.println(declaringClass);
        RandomUtils.nextLong(1, 2);
    }

    enum Test {
        ONE;
    }
}
