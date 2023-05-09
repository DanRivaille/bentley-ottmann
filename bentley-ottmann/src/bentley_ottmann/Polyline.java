package bentley_ottmann;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ivan Santos V. on 3. 5. 2023.
 */
public class Polyline {
    private final ArrayList<Point> points = new ArrayList<>();

    public Polyline(List<Point> points) {
        this.points.addAll(points);
    }

    public Point getPoint(int index) {
        return this.points.get(index);
    }

    public int getPointCount() {
        return this.points.size();
    }

    public ArrayList<Point> getPointsList() {
        return this.points;
    }

    public ArrayList<Segment> getSegments() {
        ArrayList<Segment> segments = new ArrayList<>(getPointCount() - 1);

        for (int i = 1; i < getPointCount(); ++i) {
            Point p1 = getPoint(i - 1).copy();
            Point p2 = getPoint(i).copy();

            p1.set_x_coord(p1.get_x_coord() + 0.1);

            segments.add(new Segment(p1, p2, SegmentType.POLYLINE_SEGMENT));
        }

        return segments;
    }
}
