package ru.raptors.russian_museum.find_object;

public class Line {
    public Point a;
    public Point b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public boolean doesIntercept(Line line) {
        int x1 = a.x, y = a.y, x2 = b.x;
        int x3 = line.a.x, y3 = line.a.y;
        int x4 = line.b.x, y4 = line.b.y;

        if (x3 < x1 && x4 < x1)
            return false;
        if (y3 < y && y4 < y || y3 > y && y4 > y)
            return false;
        if (x3 >= x1 && x4 >= x1)
            return true;
        if (y3 == y4)
            return true;
        if (x3 > x4) {
            int t = x3; x3 = x4; x4 = t;
            t = y3; y3 = y4; y4 = t;
        }
        int bx = x4 - x1;
        if (y == y4) {
            return true;
        }
        int b2 = (x4 - x3) / (1 + Math.abs(y - y3) / Math.abs(y - y4));
        return bx >= b2;

//        if (x1 > x2) {
//            int t = x1; x1 = x2; x2 = t;
//            t = y1; y1 = y2; y2 = t;
//        }
//        if (x3 > x4) {
//            int t = x3; x3 = x4; x4 = t;
//            t = y3; y3 = y4; y4 = t;
//        }
//        if (x1 == x2 && x3 == x4) {
//            return x1 == x3 && (y1 <= y3 && y3 <= y2 || y1 <= y4 && y4 <= y2);
//        }
//        if (x1 == x2) {
//            int k = (y4 - y3) / (x4 - x3);
//            int b = y3 - k * x3;
//            int y = k * x1 + b;
//            return y1 <= y && y <= y2 && y3 <= y && y <= y4;
//        }
//        if (x3 == x4) {
//            int k = (y2 - y1) / (x2 - x1);
//            int b = y1 - k * x1;
//            int y = k * x3 + b;
//            return y1 <= y && y <= y2 && y3 <= y && y <= y4;
//        }
//        int k1 = (y2 - y1) / (x2 - x1);
//        int k2 = (y4 - y3) / (x4 - x3);
//        int b1 = y1 - k1 * x1;
//        int b2 = y3 - k2 * x3;
//        if (k1 == k2) {
//            if (b1 == b2)
//                return (x1 <= x4 && x4 <= x2) || (x1 <= x3 && x3 <= x2);
//            else
//                return false;
//        }
//        int x = (b2 - b1) / (k1 - k2);
//        return x1 <= x && x <= x2 && x3 <= x && x <= x4;
//        //return (x1 <= x4 && x4 <= x2) || (x1 <= x3 && x3 <= x2);
    }
}
