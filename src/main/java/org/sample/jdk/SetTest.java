package org.sample.jdk;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author liudong17
 * @date 2018/11/17
 */
public class SetTest {
    private static final EnhancedRandom random = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder().stringLengthRange(10, 20).build();

    private static final int MAX = 1000000;

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void measureHashSet() {
        Set<String> stringSet = new HashSet<>();
        for (int i = 0; i < MAX; i++) {
            stringSet.add(random.nextObject(String.class));
        }

        iterate(stringSet);
    }


    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void measureLinkedHashSet() {
        Set<String> stringSet = new LinkedHashSet<>();
        for (int i = 0; i < MAX; i++) {
            stringSet.add(random.nextObject(String.class));
        }

        iterate(stringSet);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void measureTreeSet() {
        Set<String> stringSet = new TreeSet<>();
        for (int i = 0; i < MAX; i++) {
            stringSet.add(random.nextObject(String.class));
        }

        iterate(stringSet);
    }

    private void iterate(Set<String> stringSet) {
        stringSet.forEach(System.out::println);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SetTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
