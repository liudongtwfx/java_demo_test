package org.sample;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.RunnerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liudong17
 * @date 2018/11/17
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringTest {
    private static final List<Student> STRINGS = EnhancedRandom.randomListOf(20, Student.class);
    private static final Gson gson = new Gson();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void toJSON() {
        STRINGS.forEach(JSON::toJSONString);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void toGSON() {
        STRINGS.forEach(gson::toJson);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void toStringMethod() {
        STRINGS.forEach(ToStringBuilder::reflectionToString);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void originStringMethod() {
        STRINGS.forEach(Student::toString);
    }

    public static void main(String[] args) throws RunnerException {
        Constructor<?>[] constructors = String.class.getConstructors();
        List<String> str = new ArrayList<>();
        for (Constructor<?> constructor : constructors) {
            String s = "String(";
            for (Parameter parameter : constructor.getParameters()) {
                s += parameter.getType().getSimpleName() + ",";
            }
            String s1 = s.substring(0, s.length() - 1) + ")";
            str.add(s1);
        }
        str.stream().sorted().forEach(System.out::println);

        String s = "abcdef";
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.codePointAt(i));
        }
        System.out.println(s.codePointCount(0, 4));
        String another = "abcxyz";
        System.out.println(s.regionMatches(0, another, 1, 3));


        char c = 'a';
        System.out.println(String.valueOf(c));
        System.out.println(Character.toString(c));

        s.chars().boxed().forEach(System.out::println);

        String one = "abcdef";
        String anotherOne = "abcdef";
        String newOne = new String(one);
        System.out.println(one == anotherOne);
        System.out.println(one.equals(anotherOne));
        System.out.println(one.intern() == anotherOne.intern());

        System.out.println(one == newOne);
        System.out.println(one.equals(newOne));
        System.out.println(one.intern() == newOne.intern());

        String hello = one.replaceAll("[a-c]", "hello");
        System.out.println(hello);

        String random = RandomStringUtils.randomAlphabetic(10);
        System.out.println(random);
    }

    static interface ToString {
        @Override
        public String toString();
    }

    @Data
    static class Student implements ToString {
        private Integer a;
        private Integer b;
        private Integer c;
        private Integer d;
        private Integer e;
        private Integer f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private String l;
    }
}
