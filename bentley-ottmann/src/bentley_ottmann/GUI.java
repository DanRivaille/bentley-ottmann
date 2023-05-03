package bentley_ottmann;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Created by valen_000 on 15. 5. 2017.
 */

public class GUI extends JFrame {

    private ArrayList<Segment> input_data;
    private ArrayList<Point> intersections;

    private ArrayList<Polyline> polylines;
    private boolean repaint = false;

    public GUI(final ArrayList<Segment> input_data, final ArrayList<Point> intersections, final ArrayList<Polyline> polylines) {

        this.input_data = input_data;
        this.intersections = intersections;
        this.polylines = polylines;

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        setSize(1000, 1000);
        setTitle("Bentley-Ottmann algorithm");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint = !repaint;
                repaint();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }

    private void drawPoint(Graphics2D g2, Point p, Paint color) {
        double new_x = p.get_x_coord() - 6 / 2.0;
        double new_y = p.get_y_coord() - 6 / 2.0;
        Ellipse2D.Double point = new Ellipse2D.Double(new_x, new_y, 6, 6);
        g2.setPaint(color);
        g2.fill(point);
        g2.draw(point);
    }

    private void drawLine(Graphics2D g2, Segment s, Paint color) {
        Line2D.Double segment = new Line2D.Double(s.first().get_x_coord(), s.first().get_y_coord(), s.second().get_x_coord(), s.second().get_y_coord());
        g2.setPaint(color);
        g2.draw(segment);
    }

    private void drawLine(Graphics2D g2, Point point1, Point point2, Paint color) {
        drawLine(g2, new Segment(point1, point2), color);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw segments
        for(Segment s : this.input_data) {
            this.drawLine(g2, s, Color.LIGHT_GRAY);
            this.drawPoint(g2, s.first(), Color.BLACK);
            this.drawPoint(g2, s.second(), Color.BLACK);
        }

        // Draw polylines
        for (Polyline pl: this.polylines) {
            this.drawPoint(g2, pl.getPoint(0), Color.GREEN);
            for (int i = 1; i < pl.getPointCount(); ++i) {
                Point p1 = pl.getPoint(i - 1);
                Point p2 = pl.getPoint(i);

                this.drawLine(g2, p1, p2, Color.BLUE);
                this.drawPoint(g2, p2, Color.GREEN);
            }
        }

        // Draw intersections
        if(repaint) {
            g2.drawString("number of intersections: " + this.intersections.size(), 40, 70);
            for (Point p : this.intersections) {
                this.drawPoint(g2, p, Color.RED);
            }
        }

    }
}