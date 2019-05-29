package org.sample.apachecommonsdemo;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author liudong17
 * @date 2019-05-25 11:43
 */
@Slf4j
public class ClosureTest {
    public static void main(String[] args) {
        while (true) {
            List<String> strings = EnhancedRandom.randomListOf(100000, String.class);
        }
    }
}
