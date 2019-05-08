package org.sample;

/**
 * @author liudong17
 * @date 2019-01-23 14:59
 */
public class IntegerTest {
    public static void main(String[] args) {
        System.out.println(Integer.toString(10, 16));

        String unsignedMinIntegerString = Integer.toUnsignedString(Integer.MIN_VALUE);
        System.out.println(unsignedMinIntegerString);
        System.out.println(Integer.MAX_VALUE);

        Integer a = 100;
        Integer b = 100;
        Integer c = 1000;
        Integer d = 1000;
        System.out.println(a == b);
        System.out.println(c == d);
        Integer e = new Integer(100);
        Integer f = Integer.valueOf(100);
        System.out.println(a == e);
        System.out.println(a == f);
    }
}
