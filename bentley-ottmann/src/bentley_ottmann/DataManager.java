package bentley_ottmann;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    //private String filename = "C:\\Users\\Spider.cl\\Repositories\\points.csv"; // For Windows
    private String filename = "/Users/dansantos/Repositories/points.csv"; // For Mac

    public void savePoints(ArrayList<Segment> data) {
        try {
            File csvFile = new File(this.filename);
            FileWriter fileWriter = new FileWriter(csvFile);

            for (Segment segment: data) {

                String line = String.valueOf(segment.first().get_x_coord()) +
                        ';' +
                        segment.first().get_y_coord() +
                        ';' +
                        segment.second().get_x_coord() +
                        ';' +
                        segment.second().get_y_coord() +
                        '\n';
                fileWriter.write(line);
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Segment> loadPoints() {
        ArrayList<Segment> data = new ArrayList<>();

        try {
            List<String> allLines = Files.readAllLines(Paths.get(this.filename));

            for (String line : allLines) {
                String[] valuesStringList = line.split(";");

                Point p1 = new Point(Double.parseDouble(valuesStringList[0]), Double.parseDouble(valuesStringList[1]));
                Point p2 = new Point(Double.parseDouble(valuesStringList[2]), Double.parseDouble(valuesStringList[3]));
                data.add(new Segment(p1, p2));
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return data;
    }
}
