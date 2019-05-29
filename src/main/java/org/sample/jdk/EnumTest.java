package org.sample.jdk;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.Data;

/**
 * @author liudong17
 * @date 2019-01-23 16:19
 */
public class EnumTest {
    public static void main(String[] args) {
        Gson gson = new Gson();
        B b = new B();
        b.setName("123");
        System.out.println(JSON.toJSONString(b));
        System.out.println(gson.toJson(b));
    }

    @Data
    private static class A {
        private String name;
    }

    @Data
    private static class B extends A {
        private String name;
    }
}
