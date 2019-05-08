package org.sample.effective_java.equals;

import java.util.Objects;

/**
 * @author liudong17
 * @date 2019-03-26 15:44
 */

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) {
        ColorPoint colorPoint = new ColorPoint(1, 2, "RED");
        Point point = new Point(1, 2);
        ColorPoint colorPointGreen = new ColorPoint(1, 2, "Green");
        System.out.println(colorPoint.equals(point));
        System.out.println(point.equals(colorPointGreen));
        System.out.println(colorPoint.equals(colorPointGreen));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }

    public static class ColorPoint extends Point {
        private String color;

        private ColorPoint(int x, int y, String color) {
            super(x, y);
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) {
                return false;
            }
            if (!(o instanceof ColorPoint)) {
                return o.equals(this);
            }
            return super.equals(o) && Objects.equals(color, ((ColorPoint) o).color);
        }
    }
}
