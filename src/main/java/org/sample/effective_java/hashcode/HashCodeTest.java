package org.sample.effective_java.hashcode;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author liudong17
 * @date 2019-04-08 20:27
 */
public class HashCodeTest {
    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        students.add(new Student("123"));
        System.out.println(students.contains(new Student("123")));
        System.out.println(Objects.hash());
    }

    static class Student {
        private String name;

        Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Student) && Objects.equals(name, ((Student) obj).name);
        }
    }


    @Data
    static class Teacher {
        private String name;
        private Integer age;
    }
}
