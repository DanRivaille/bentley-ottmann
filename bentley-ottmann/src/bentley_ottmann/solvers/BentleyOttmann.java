package bentley_ottmann.solvers;

import bentley_ottmann.*;

import java.util.*;

/**
 * Created by valen_000 on 14. 5. 2017.
 *
 * Edited by Ivan Santos V. on 3. 5. 2023.
 */

public class BentleyOttmann extends IntersectionsSolverImpl {

    private final Queue<Event> Q;
    private final NavigableSet<Segment> T;
    private final int numberOfPolylinesSegments;
    private int numberOfPolylinesSegmentsProcesses = 0;
    private int currentPolylinesSegmentsInProcess = 0;

    public BentleyOttmann(ArrayList<Segment> input_data, int numberOfPolylinesSegments) {
        super();
        this.Q = new PriorityQueue<>(new event_comparator());
        this.T = new TreeSet<>(new segment_comparator());
        this.numberOfPolylinesSegments = numberOfPolylinesSegments;
        for(Segment s : input_data) {
            this.Q.add(new Event(s.first(), s, EventType.INITIAL_ENDPOINT));
            this.Q.add(new Event(s.second(), s, EventType.FINAL_ENDPOINT));
        }
    }

    private void processInitialEndpoint(Event e, double L) {
        for(Segment s : e.get_segments()) {
            if (SegmentType.POLYLINE_SEGMENT == s.getType()) {
                this.numberOfPolylinesSegmentsProcesses++;
                this.currentPolylinesSegmentsInProcess++;
            }

            this.recalculate(L);
            this.T.add(s);
            if(this.T.lower(s) != null) {
                Segment r = this.T.lower(s);
                this.report_intersection(r, s, L);
            }
            if(this.T.higher(s) != null) {
                Segment t = this.T.higher(s);
                this.report_intersection(t, s, L);
            }
            if(this.T.lower(s) != null && this.T.higher(s) != null) {
                Segment r = this.T.lower(s);
                Segment t = this.T.higher(s);
                this.remove_future(r, t);
            }
        }
    }

    private void processFinalEndpoint(Event e, double L) {
        for(Segment s : e.get_segments()) {
            if(this.T.lower(s) != null && this.T.higher(s) != null) {
                Segment r = this.T.lower(s);
                Segment t = this.T.higher(s);
                this.report_intersection(r, t, L);
            }
            this.T.remove(s);

            if (SegmentType.POLYLINE_SEGMENT == s.getType())
                this.currentPolylinesSegmentsInProcess--;
        }
    }

    private void processCrossEndpoint(Event e, double L) {
        Segment s_1 = e.get_segments().get(0);
        Segment s_2 = e.get_segments().get(1);
        this.swap(s_1, s_2);
        if(s_1.get_value() < s_2.get_value()) {
            computeBelowAndAboveSegments(s_1, s_2, L);
        } else {
            computeBelowAndAboveSegments(s_2, s_1, L);
        }

        if (isVertexSegment(s_1) ^ isVertexSegment(s_2))
            this.X.add(e.get_point());
    }

    private boolean isVertexSegment(Segment segment) {
        return SegmentType.VERTEX_SEGMENT == segment.getType();
    }

    private void computeBelowAndAboveSegments(Segment s_1, Segment s_2, double L) {
        if(this.T.higher(s_1) != null) {
            Segment t = this.T.higher(s_1);
            this.report_intersection(t, s_1, L);
            this.remove_future(t, s_2);
        }
        if(this.T.lower(s_2) != null) {
            Segment r = this.T.lower(s_2);
            this.report_intersection(r, s_2, L);
            this.remove_future(r, s_1);
        }
    }

    @Override
    public void findIntersections() {
        int i = 0;
        while(!this.Q.isEmpty()) {
            Event e = this.Q.poll();
            switch (e.get_type()) {
                case INITIAL_ENDPOINT -> processInitialEndpoint(e, e.get_value());
                case FINAL_ENDPOINT   -> processFinalEndpoint(e, e.get_value());
                case CROSS_POINT      -> processCrossEndpoint(e, e.get_value());
            }
            i++;
            if ((this.numberOfPolylinesSegments == this.numberOfPolylinesSegmentsProcesses) && (this.currentPolylinesSegmentsInProcess == 0))
                break;
        }
        System.out.println(i);
    }

    private void report_intersection(Segment s_1, Segment s_2, double L) {
        Point intersection = computeIntersection(s_1.first(), s_1.second(), s_2.first(), s_2.second());

        if ((intersection != null) && (intersection.get_x_coord() > L)) {
            this.Q.add(new Event(intersection, new ArrayList<>(Arrays.asList(s_1, s_2)), EventType.CROSS_POINT));
        }
    }

    private void remove_future(Segment s_1, Segment s_2) {
        for(Event e : this.Q) {
            if(EventType.CROSS_POINT == e.get_type()) {
                if((e.get_segments().get(0) == s_1 && e.get_segments().get(1) == s_2) || (e.get_segments().get(0) == s_2 && e.get_segments().get(1) == s_1)) {
                    this.Q.remove(e);
                    break;
                }
            }
        }
    }

    private void swap(Segment s_1, Segment s_2) {
        this.T.remove(s_1);
        this.T.remove(s_2);
        double value = s_1.get_value();
        s_1.set_value(s_2.get_value());
        s_2.set_value(value);
        this.T.add(s_1);
        this.T.add(s_2);
    }

    private void recalculate(double L) {
        for (Segment segment : this.T) {
            segment.calculate_value(L);
        }
    }

    private static class event_comparator implements Comparator<Event> {
        @Override
        public int compare(Event e_1, Event e_2) {
            return Double.compare(e_1.get_value(), e_2.get_value());
        }
    }

    private static class segment_comparator implements Comparator<Segment> {
        @Override
        public int compare(Segment s_1, Segment s_2) {
            return Double.compare(s_2.get_value(), s_1.get_value());
        }
    }
}