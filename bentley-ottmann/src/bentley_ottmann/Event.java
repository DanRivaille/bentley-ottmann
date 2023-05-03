package bentley_ottmann;

import java.util.*;

/**
 * Created by valen_000 on 14. 5. 2017.
 */

public class Event {

    private Point point;
    private final ArrayList<Segment> segments;
    private double value;
    private EventType type;

    Event(Point p, Segment s, EventType type) {
        this.point = p;
        this.segments = new ArrayList<>(Arrays.asList(s));
        this.value = p.get_x_coord();
        this.type = type;
    }

    Event(Point p, ArrayList<Segment> s, EventType type) {
        this.point = p;
        this.segments = s;
        this.value = p.get_x_coord();
        this.type = type;
    }

    public void add_point(Point p) {
        this.point = p;
    }

    public Point get_point() {
        return this.point;
    }

    public void add_segment(Segment s) {
        this.segments.add(s);
    }

    public ArrayList<Segment> get_segments() {
        return this.segments;
    }

    public void set_type(EventType type) {
        this.type = type;
    }

    public EventType get_type() {
        return this.type;
    }

    public void set_value(double value) {
        this.value = value;
    }

    public double get_value() {
        return this.value;
    }

}
