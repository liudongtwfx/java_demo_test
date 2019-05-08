package org.sample.effective_java.clone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudong17
 * @date 2019-04-08 21:27
 */
public class CloneTest {
    public static void main(String[] args) {
        A a = new A("Liu");
        a.addNumber(12);
        a.addNumber(34);
        A another = a.clone();
        System.out.println(another);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class A implements Cloneable {
        private final Integer[] numbers = new Integer[10];
        private int index = 0;
        private String name;

        A(String name) {
            this.name = name;
        }

        public void addNumber(Integer a) {
            numbers[index++] = a;
        }

        @Override
        public A clone() {
            try {
                return (A) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException();
            }
        }
    }
}
