package org.sample;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author liudong17
 * @date 2018-11-22
 */
@Slf4j
public class DeadLockSample extends Thread {

    private String first;
    private String second;

    DeadLockSample(String name, String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained:" + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained:" + second);
                }
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(10);
    }

    public void fun(Number n) {
        n.byteValue();
    }
}
