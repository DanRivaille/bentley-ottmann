package bentley_ottmann.solvers;

import bentley_ottmann.*;

import java.util.ArrayList;

public class GreedySolver extends IntersectionsSolverImpl {

    private final ArrayList<Point> points;
    private final ArrayList<Polyline> polylines;

    public GreedySolver(ArrayList<Point> points, ArrayList<Polyline> polylines) {
        super();
        this.points = points;
        this.polylines = polylines;
    }

    @Override
    public void findIntersections() {
        int N = this.points.size();

        for (int i = 0; i < N - 1; ++i) {
            for (int j = i + 1; j < N; ++j) {
                for (Polyline polyline: this.polylines) {
                    for (Segment s : polyline.getSegments()) {
                        Point p1 = this.points.get(i);
                        Point p2 = this.points.get(j);

                        Point intersection = computeIntersection(p1, p2, s.first(), s.second());
                        if (intersection != null) {
                            this.X.add(intersection);
                            break;
                        }
                    }
                }
            }
        }
    }
}
