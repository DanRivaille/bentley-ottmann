package bentley_ottmann.solvers;

import bentley_ottmann.IntersectionsSolver;
import bentley_ottmann.Point;

import java.util.ArrayList;

public abstract class IntersectionsSolverImpl implements IntersectionsSolver {
    protected final ArrayList<Point> X;

    public IntersectionsSolverImpl() {
        this.X = new ArrayList<>();
    }

    protected Point computeIntersection(Point p1, Point p2, Point p3, Point p4) {
        double x1 = p1.get_x_coord();
        double y1 = p1.get_y_coord();
        double x2 = p2.get_x_coord();
        double y2 = p2.get_y_coord();
        double x3 = p3.get_x_coord();
        double y3 = p3.get_y_coord();
        double x4 = p4.get_x_coord();
        double y4 = p4.get_y_coord();
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
