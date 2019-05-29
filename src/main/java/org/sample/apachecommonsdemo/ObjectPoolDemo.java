package org.sample.apachecommonsdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author liudong17
 * @date 2019-05-13 21:06
 */
@Slf4j
public class ObjectPoolDemo {
    public static void main(String[] args) {
        try (ObjectPool<Student> objectPool = new GenericObjectPool<>(new DemoObjectPoolFactory())) {
            for (int i = 0; i < 10; i++) {
                Student s = objectPool.borrowObject();
                System.out.println("result:" + s.hashCode());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("", e);
        }
    }

    private static class DemoObjectPoolFactory extends BasePooledObjectFactory<Student> {

        @Override
        public Student create() throws Exception {
            Student helloWorld = new Student("hello world");
            System.out.println("create:" + helloWorld.hashCode());
            return helloWorld;
        }

        @Override
        public PooledObject<Student> wrap(Student obj) {
            return new DefaultPooledObject<>(obj);
        }
    }


    @AllArgsConstructor
    private static class Student {
        private String name;
    }
}
