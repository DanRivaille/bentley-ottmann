package bentley_ottmann;

import bentley_ottmann.solvers.BentleyOttmann;
import bentley_ottmann.solvers.GreedySolver;

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

        DataManager dataManager = new DataManager();

        Point p1 = new Point(550, 200);
        Point p2 = new Point(650, 450);
        Point p3 = new Point(450, 700);
        Point p4 = new Point(660, 850);

        Polyline polyline = new Polyline(Arrays.asList(p1, p2, p3, p4));
        ArrayList<Polyline> polylines = new ArrayList<>();
        polylines.add(polyline);

        ArrayList<Segment> data = new ArrayList<>();
        /*
        int aux = 3;



        switch (aux) {
            case 0 -> addPoints1(data);
            case 1 -> addPoints2(data);
            case 2 -> data = addPoints3();
            case 3 -> data = dataManager.loadPoints();
        }

         */
        //dataManager.savePoints(data);

        int numberOfPolylinesSegments = 0;
        for (Polyline pl: polylines) {
            ArrayList<Segment> segments = pl.getSegments();

            numberOfPolylinesSegments += segments.size();
            data.addAll(segments);
        }
/*

 */


        IntersectionsSolver test;

        ArrayList<Point> points = new ArrayList<>();

        for(int i = 0; i < 3700; i++) {
            Point p_1 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            points.add(p_1);
        }

        ArrayList<Segment> segments = preprocessData(points);
        data.addAll(segments);

        test = switch (2) {
            case 1 -> new BentleyOttmann(data, numberOfPolylinesSegments);
            case 2 -> new GreedySolver(points, polylines);
            default -> null;
        };


        long t1 = System.currentTimeMillis();
        test.findIntersections();
        long t2 = System.currentTimeMillis();

        ArrayList<Point> intersections = test.getIntersections();

        //new GUI(data, intersections, polylines);

        System.out.println("number of intersections: " + intersections.size());
        System.out.println("runtime: " + (t2 - t1) + " ms");
    }

    private static ArrayList<Segment> preprocessData(List<Point> points) {
        ArrayList<Segment> segments = new ArrayList<>();

        int N = points.size();
        double offset = 1.0 / (N * N);
        double current_offset = 0;
        for (int i = 0; i < N - 1; ++i) {
            for (int j = i + 1; j < N; ++j) {
                Point p1 = points.get(i).copy();
                Point p2 = points.get(j).copy();

                p1.set_x_coord(p1.get_x_coord() + current_offset);
                p2.set_x_coord(p2.get_x_coord() + current_offset);

                segments.add(new Segment(p1, p2, SegmentType.VERTEX_SEGMENT));

                current_offset += offset;
            }
        }

        return segments;
    }

    private static double rand(double range_min, double range_max) {
        Random r = new Random();
        return range_min + (r.nextDouble() * (range_max - range_min));
    }


    private static void addPoints1(ArrayList<Segment> data) {
        for(int i = 0; i < 10; i++) {
            Point p_1 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            Point p_2 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            data.add(new Segment(p_1, p_2, SegmentType.VERTEX_SEGMENT));
        }
    }

    private static void addPoints2(ArrayList<Segment> data) {
        int numberOfPointsPerSide = 4;
        double intervalRange = (range_max - range_min) / (numberOfPointsPerSide + 1);
        for(int i = 0; i < numberOfPointsPerSide; i++) {
            Point p_1 = new Point(200, (i + 1) * intervalRange);
            Point p_2 = new Point(700, (i + 2) * intervalRange);
            data.add(new Segment(p_1, p_2, SegmentType.VERTEX_SEGMENT));
        }
        Point p5 = new Point(300, 300);
        Point p6 = new Point(300, 700);
        data.add(new Segment(p5, p6, SegmentType.VERTEX_SEGMENT));
    }

    private static ArrayList<Segment> addPoints3() {
        int numberOfPointsPerSide = 2;
        double intervalRange = (range_max - range_min) / (numberOfPointsPerSide + 1);
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < numberOfPointsPerSide; i++) {
            Point p_1 = new Point(200 + (i * 10), (i + 1) * intervalRange);
            Point p_2 = new Point(700 + (i * 10), (i + 2) * intervalRange);
            points.add(p_1);
            points.add(p_2);
        }

        return preprocessData(points);
    }

    private static void savePoints(ArrayList<Segment> data) {

    }

    private static ArrayList<Segment> loadPoints() {
        return null;
    }
}
