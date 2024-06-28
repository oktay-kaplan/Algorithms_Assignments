import java.util.ArrayList;

public class MetroStop {
    int xCoord, yCoord, metroLine;
    String stopDescription;
    ArrayList<Edge> connections;

    public MetroStop(int xCoord, int yCoord, int metroLine, String stopDescription) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.connections = new ArrayList<>();
        this.metroLine = metroLine;
        this.stopDescription = stopDescription;
    }

    public String toString() {
        return this.stopDescription;
    }
}