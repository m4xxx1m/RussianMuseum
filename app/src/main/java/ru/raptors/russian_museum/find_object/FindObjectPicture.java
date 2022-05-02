package ru.raptors.russian_museum.find_object;

import java.util.ArrayList;
import java.util.Scanner;

public class FindObjectPicture {
    public String title;
    public int width;
    public int height;
    public int numberOfPoints;
    public ArrayList<Point> points;
    private ArrayList<Line> lines;
    public int minX = 1000000000, maxX = -1, minY = 1000000000, maxY = -1;

    public FindObjectPicture(String input) {
        Scanner sc = new Scanner(input);
        width = sc.nextInt();
        height = sc.nextInt();
        numberOfPoints = sc.nextInt();
        points = new ArrayList<>(numberOfPoints);
        for (int i = 0; i < numberOfPoints; ++i) {
            points.add(new Point(sc.nextInt(), sc.nextInt()));
        }
        title = sc.nextLine().substring(1);
        setMinAndMax();
    }

    public float getAspectRatio() {
        return (float) width / height;
    }

    public ArrayList<Line> getLines() {
        if (lines == null) {
            setLines();
        }
        return lines;
    }

    private void setMinAndMax() {
        for (Point p : points) {
            if (p.x > maxX)
                maxX = p.x;
            if (p.x < minX)
                minX = p.x;
            if (p.y > maxY)
                maxY = p.y;
            if (p.y < minY)
                minY = p.y;
        }
    }

    private void setLines() {
        lines = new ArrayList<>(numberOfPoints);
        for (int i = 0; i < numberOfPoints; ++i) {
            lines.add(new Line(points.get(i), points.get((i+1) % numberOfPoints)));
        }
    }

}
