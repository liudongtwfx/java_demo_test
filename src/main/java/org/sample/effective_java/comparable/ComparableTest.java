package org.sample.effective_java.comparable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author liudong17
 * @date 2019-04-09 16:48
 */
public class ComparableTest {
    public static void main(String[] args) {
        Set<A> aset = new TreeSet<>();
        aset.add(new A("a"));
        aset.add(new A("d"));
        aset.add(new A("c"));
        aset.add(new A("f"));
        System.out.println(aset);

        Set<BigDecimal> hashSet = new HashSet<>();
        hashSet.add(new BigDecimal("1.0"));
        hashSet.add(new BigDecimal("1.00"));
        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        treeSet.add(new BigDecimal("1.00"));
        System.out.println(hashSet.size());
        System.out.println(treeSet.size());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class A implements Comparable<A> {
        private String name;

        @Override
        public int compareTo(A o) {
            return this.name.compareTo(o.getName());
        }
    }
}
