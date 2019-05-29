package org.sample.jdk;

import java.util.BitSet;

/**
 * @author liudong17
 * @date 2019-01-23 18:10
 */
public class BitSetMapTest {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(10);
        System.out.println(bitSet.get(10));
        System.out.println(bitSet.get(11));
        int length = bitSet.length();
        System.out.println(length);
    }
}
