package org.sample.jdk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liudong17
 * @date 2019-01-23 17:31
 */
public class ListTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<Integer> integers = new ArrayList<>();
        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);
        for (int i = 0; i < 100; i++) {
            Object[] o = (Object[]) field.get(integers);
            int beforeAdd = o.length;
            integers.add(i);
            Object[] after = (Object[]) field.get(integers);
            int afterAdd = after.length;
            if (beforeAdd != afterAdd) {
                System.out.println(beforeAdd + " " + afterAdd);
            }
        }
    }
}
