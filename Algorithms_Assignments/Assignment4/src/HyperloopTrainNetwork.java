import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;
    public final double averageWalkingSpeed = 1000 / 6.0;;


    public ArrayList<MetroStop> metroStops = new ArrayList<>();


    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]*)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+(?:\\.[0-9]*)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Point p = new Point(0, 0);
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            p = new Point(x, y);
        }
        return p;
    }

    /**
     * Function to extract the train lines from the fileContent by reading train line names and their
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();

        String[] lines = fileContent.split("\n");

        String trainLineName = null;
        List<Station> stations = new ArrayList<>();

        int numberOfLines = 1;

        for (String line : lines) {
            ArrayList<MetroStop> currentMetroLineMetroStops = new ArrayList<>();

            line = line.trim();
            if (line.startsWith("train_line_name")) {
                if (trainLineName != null && !stations.isEmpty()) {
                    trainLines.add(new TrainLine(trainLineName, new ArrayList<>(stations)));
                    stations.clear();
                }
                trainLineName = getStringVar("train_line_name", line);
            } else if (line.startsWith("train_line_stations")) {
                String stationsString = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
                String[] stationPairs = stationsString.split("\\s*\\)\\s*\\(");
                for (String stationPair : stationPairs) {
                    String[] coordinates = stationPair.trim().split("\\s*,\\s*");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    MetroStop newMetroStop = new MetroStop(x, y, numberOfLines,   trainLineName + " Line Station " + (currentMetroLineMetroStops.size()+1));
                    metroStops.add(newMetroStop);
                    currentMetroLineMetroStops.add(newMetroStop);
                    stations.add(new Station(new Point(x, y), trainLineName + " Line Station " + (currentMetroLineMetroStops.size())));
                }
                for (int i = 1; i< currentMetroLineMetroStops.size(); i++)
                    UrbanTransportationApp.addConnectionBetweenStops(currentMetroLineMetroStops.get(i-1), currentMetroLineMetroStops.get(i), averageTrainSpeed);
                numberOfLines++;
            }
        }
        if (trainLineName != null && !stations.isEmpty()) {
            trainLines.add(new TrainLine(trainLineName, new ArrayList<>(stations)));
        }

        return trainLines;
    }
    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            StringBuilder fileContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            numTrainLines = getIntVar("num_train_lines", fileContent.toString());
            averageTrainSpeed = getDoubleVar("average_train_speed", fileContent.toString());
            averageTrainSpeed = averageTrainSpeed*100/6.0;
            startPoint = new Station(getPointVar("starting_point", fileContent.toString()), "Starting Point");
            destinationPoint = new Station(getPointVar("destination_point", fileContent.toString()),"Final Destination");
            lines = getTrainLines(fileContent.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}