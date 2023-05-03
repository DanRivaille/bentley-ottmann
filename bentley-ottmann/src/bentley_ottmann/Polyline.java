package bentley_ottmann;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Santos V. on 3. 5. 2023.
 */
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
