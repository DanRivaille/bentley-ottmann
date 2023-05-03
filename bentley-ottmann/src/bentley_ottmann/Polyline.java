package bentley_ottmann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polyline {
    private ArrayList<Point> points = new ArrayList<>(4);

    public Point getPoint(int index) {
        return this.points.get(index);
    }

    public int getPointCount() {
        return this.points.size();
    }

    public Polyline(List<Point> points) {
        this.points.addAll(points);
    }
}
