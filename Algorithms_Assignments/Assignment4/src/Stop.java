import java.util.ArrayList;

public class Stop {
    int xCoordinate, yCoordinate, metroLine;
    String description;
    ArrayList<Edge> edgeList;

    public Stop(int xCoordinate, int yCoordinate, int metroLine, String description) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.edgeList = new ArrayList<>();
        this.metroLine = metroLine;
        this.description = description;
    }

    public String toString() {
        return this.description;
    }
}