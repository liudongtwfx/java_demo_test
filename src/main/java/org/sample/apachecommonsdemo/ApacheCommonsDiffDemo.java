package org.sample.apachecommonsdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liudong17
 * @date 2019-04-19 13:52
 */
@Slf4j
public class ApacheCommonsDiffDemo {
    public static void main(String[] args) {
        Student one = new Student("liudong", 12);
        Student two = new Student("liudong1", 14);
        DiffResult diff = ObjectToDiff.of(one, two).diff();
        log.info(getDiff(diff));
    }

    private static String getDiff(DiffResult diffResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Diff<?> diff : diffResult) {
            stringBuilder.append(diff.getFieldName()).append(": 修改前:").append(diff.getLeft()).append(",修改后:").append(diff.getRight());
            stringBuilder.append("  ");
        }
        return stringBuilder.toString();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface ChineseName {
        String value();
    }

    private interface EnableDiffLogable {

    }

    static class ObjectToDiff<T extends EnableDiffLogable> {
        private T before;
        private T after;

        private ObjectToDiff(T before, T after) {
            this.before = before;
            this.after = after;
        }

        static <T extends EnableDiffLogable> ObjectToDiff<T> of(T before, T after) {
            return new ObjectToDiff<>(before, after);
        }

        DiffResult diff() {
            DiffBuilder diffBuilder = new DiffBuilder(before, after, ToStringStyle.DEFAULT_STYLE);
            DiffFieldNameAndChineseNameUtils.getDiffNameMap(before.getClass()).forEach((field, chineseName) -> {
                field.setAccessible(true);
                try {
                    diffBuilder.append(chineseName, field.get(before), field.get(after));
                } catch (Exception e) {
                    log.error("获取class:{} 变量:{} 发生异常", field.getDeclaringClass(), chineseName, e);
                }
            });
            return diffBuilder.build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Student implements EnableDiffLogable {
        // @ChineseName("姓名")
        private String name;

        @ChineseName("年龄")
        private Integer age;
    }

    static class DiffFieldNameAndChineseNameUtils {
        static Map<Class<?>, Map<Field, String>> CLASS_DIFF_NAME_MAP = new HashMap<>();

        private DiffFieldNameAndChineseNameUtils() {
        }

        /**
         * 获取需要比较diff的field
         *
         * @return
         */
        static Map<Field, String> getDiffNameMap(Class<?> clazz) {
            if (CLASS_DIFF_NAME_MAP.containsKey(clazz)) {
                return CLASS_DIFF_NAME_MAP.get(clazz);
            }
            Map<Field, String> nameMap = new HashMap<>();
            for (Field field : FieldUtils.getFieldsWithAnnotation(clazz, ChineseName.class)) {
                nameMap.put(field, field.getAnnotation(ChineseName.class).value());
            }
            CLASS_DIFF_NAME_MAP.put(clazz, nameMap);
            return nameMap;
        }
    }
}