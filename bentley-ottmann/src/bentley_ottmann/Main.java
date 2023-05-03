package bentley_ottmann;

import java.util.*;

/**
 * Created by valen_000 on 14. 5. 2017.
 *
 * Edited by Ivan Santos V. on 3. 5. 2023.
 */

public class Main {
    static double range_min = 100;
    static double range_max = 900;

    public static void main(String[] args) {



        Point p1 = new Point(550, 200);
        Point p2 = new Point(650, 450);
        Point p3 = new Point(450, 700);
        Point p4 = new Point(750, 850);
        Polyline polyline = new Polyline(Arrays.asList(p1, p2, p3, p4));
        ArrayList<Polyline> polylines = new ArrayList<>();
        polylines.add(polyline);

        ArrayList<Segment> data = new ArrayList<>();
        int aux = 1;

        switch (aux) {
            case 0 -> addPoints1(data);
            case 1 -> addPoints2(data);
            case 2 -> addPoints3(data);
        }

        for (Polyline pl: polylines) {
            for (int i = 1; i < pl.getPointCount(); ++i) {
                Point point1 = pl.getPoint(i - 1);
                Point point2 = pl.getPoint(i);
                data.add(new Segment(point1, point2));
            }
        }

        BentleyOttmann test = new BentleyOttmann(data);

        long t1 = System.currentTimeMillis();
        test.find_intersections();
        long t2 = System.currentTimeMillis();

        //test.print_intersections();
        ArrayList<Point> intersections = test.get_intersections();

        new GUI(data, intersections, polylines);

        System.out.println("number of intersections: " + intersections.size());
        System.out.println("runtime: " + (t2 - t1) + " ms");
    }

    private static double rand(double range_min, double range_max) {
        Random r = new Random();
        return range_min + (r.nextDouble() * (range_max - range_min));
    }


    private static void addPoints1(ArrayList<Segment> data) {
        for(int i = 0; i < 4; i++) {
            Point p_1 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            Point p_2 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            data.add(new Segment(p_1, p_2));
        }
    }

    private static void addPoints2(ArrayList<Segment> data) {
        int numberOfPointsPerSide = 4;
        double intervalRange = (range_max - range_min) / (numberOfPointsPerSide + 1);
        for(int i = 0; i < numberOfPointsPerSide; i++) {
            Point p_1 = new Point(200, (i + 1) * intervalRange);
            Point p_2 = new Point(700, (i + 2) * intervalRange);
            data.add(new Segment(p_1, p_2));
        }
    }

    private static void addPoints3(ArrayList<Segment> data) {
        int numberOfPointsPerSide = 4;
        double intervalRange = (range_max - range_min) / (numberOfPointsPerSide + 1);
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < numberOfPointsPerSide; i++) {
            Point p_1 = new Point(200 + (i * 10), (i + 1) * intervalRange);
            Point p_2 = new Point(700 + (i * 10), (i + 2) * intervalRange);
            points.add(p_1);
            points.add(p_2);
        }

        for (int i = 0; i < points.size(); ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                Point p_1 = points.get(i);
                Point p_2 = points.get(j);
                data.add(new Segment(p_1, p_2));
            }
        }
    }
}
