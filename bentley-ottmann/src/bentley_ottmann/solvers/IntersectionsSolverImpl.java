package bentley_ottmann.solvers;

import bentley_ottmann.IntersectionsSolver;
import bentley_ottmann.Point;
import bentley_ottmann.Polyline;
import bentley_ottmann.Segment;

import java.util.ArrayList;

public abstract class IntersectionsSolverImpl implements IntersectionsSolver {
    private final ArrayList<Point> X;

    public IntersectionsSolverImpl() {
        this.X = new ArrayList<>();
    }

    private Point reportIntersection(Point p1, Point p2, Segment s_2) {
        double x1 = p1.get_x_coord();
        double y1 = p1.get_y_coord();
        double x2 = p2.get_x_coord();
        double y2 = p2.get_y_coord();
        double x3 = s_2.first().get_x_coord();
        double y3 = s_2.first().get_y_coord();
        double x4 = s_2.second().get_x_coord();
        double y4 = s_2.second().get_y_coord();
        double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);
        if(r != 0) {
            double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
            double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
            if(t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double x_c = x1 + t * (x2 - x1);
                double y_c = y1 + t * (y2 - y1);
                return new Point(x_c, y_c);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Point> getIntersections() {
        return this.X;
    }
}
