package org.sample.jdk;

/**
 * @author liudong17
 * @date 2018-11-28
 */
public class EscapeTest {

    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc(i);
        }
        // 查看执行时间
        long a2 = System.currentTimeMillis();
        System.out.println("cost " + (a2 - a1) + " ms");
        // 为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc(int num) {
        User user = new User(num);
    }

    static class User {
        private int num;

        User(int num) {
            this.num = num;
        }
    }
}
