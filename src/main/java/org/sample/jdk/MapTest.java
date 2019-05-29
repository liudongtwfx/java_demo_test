package org.sample.jdk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liudong17
 * @date 2019-01-23 19:04
 */
@Slf4j
public class MapTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>(16);
        map.put("3", 1);
        map.put("2", 2);
        map.put("1", 3);
        map.keySet().forEach(MapTest::logObject);

        Map<String, Integer> linkedHashMap = new LinkedHashMap<>(16);
        linkedHashMap.put("3", 1);
        linkedHashMap.put("2", 2);
        linkedHashMap.put("1", 3);
        linkedHashMap.keySet().forEach(MapTest::logObject);

        Map<String, String> mergeTest = new HashMap<>(16);
        mergeTest.put("123", "abc");
        mergeTest.merge("123", "def", (v1, v2) -> StringUtils.join(new String[]{v1, v2}, ','));
        new ArrayList<>();
        log.info(mergeTest.get("123"));
    }

    private static void logObject(Object o) {
        log.info(o.toString());
    }
}
