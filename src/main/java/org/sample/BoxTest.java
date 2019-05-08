package org.sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author liudong17
 * @date 2018/11/17
 */
public class BoxTest {
    private static final int total = 1000000000;

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void meassureInteger() {
        Integer sum = 0;
        for (int i = 0; i < total; i++) {
            Integer a = new Integer(i);
            testUnBox(a);
        }
        System.out.println(sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void meassureBoxing() {
        Integer sum = 0;
        for (int i = 0; i < total; i++) {
            testBox(i);
        }
        System.out.println(sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void meassureOrigin() {
        int sum = 0;
        for (int i = 0; i < total; i++) {
            int a = i;
            testUnBox(a);
        }
        System.out.println(sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BoxTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }


    private void testBox(Integer box) {
    }


    private void testUnBox(int box) {
    }
}
