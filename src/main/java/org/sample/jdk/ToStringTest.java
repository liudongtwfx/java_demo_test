package org.sample.jdk;

import java.util.Objects;

import static java.lang.System.out;

/**
 * @author liudong17
 * @date 2019-03-15 11:53
 */
public class ToStringTest {
    public static void main(String[] args) {
        CaseInsensitiveString string = new CaseInsensitiveString("abc");
        String another = "abc";
        out.println();
        out.println(Objects.equals(string, another));
        out.println(another.equals(string));
    }

    static class CaseInsensitiveString {
        private final String s;

        CaseInsensitiveString(String s) {
            this.s = Objects.requireNonNull(s);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CaseInsensitiveString) {
                return s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);
            }
            if (obj instanceof String) {
                return s.equalsIgnoreCase((String) obj);
            }
            return false;
        }
    }
}
