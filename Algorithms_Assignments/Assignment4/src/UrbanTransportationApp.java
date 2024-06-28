import java.io.Serializable;
import java.util.ArrayList;

import java.util.*;

public class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    public static final double walkingVelocity = 1000/6.0;

    public static final HashMap<MetroStop, MetroStop> previousStopsMap = new HashMap<>();
    public static final HashMap<Set<MetroStop>, Double> stopPairTravelTimes = new HashMap<>();
    public static MetroStop finalDestination;


    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function to calculate the fastest route from the user's desired starting point to
     * the desired destination point, taking into consideration the hyperloop train
     * network.
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();
        MetroStop startingPoint = new MetroStop(network.startPoint.coordinates.x,network.startPoint.coordinates.y,0, "Starting Point");

        finalDestination = new MetroStop(network.destinationPoint.coordinates.x,network.destinationPoint.coordinates.y,0, "Final Destination");

        ArrayList<MetroStop> metroStops = network.metroStops;

        metroStops.add(startingPoint);
        metroStops.add(finalDestination);

        for (int i = 0; i < metroStops.size(); i++) {
            for (int j = i + 1; j < metroStops.size(); j++) {
                MetroStop start = metroStops.get(i);
                MetroStop end = metroStops.get(j);
                addConnectionBetweenStops(start, end, walkingVelocity);
            }
        }
        System.out.printf("The fastest route takes %d minute(s).", Math.round(calculateShortestRoute(startingPoint, finalDestination)));

        MetroStop metroStop = finalDestination;
        List<MetroStop> shortestPath = new ArrayList<>();
        while (previousStopsMap.get(metroStop) != null) {
            shortestPath.add(previousStopsMap.get(metroStop));
            metroStop = previousStopsMap.get(metroStop);
        }
        shortestPath.add(0, finalDestination);
        Collections.reverse(shortestPath);

        if(shortestPath.size()==2){
            MetroStop src = shortestPath.get(0);
            MetroStop dst = shortestPath.get(1);
            Set<MetroStop> set = new HashSet<>();
            set.add(src);
            set.add(dst);
            double time = stopPairTravelTimes.get(set);
            routeDirections.add(new RouteDirection(src.stopDescription, dst.stopDescription, time, false));

            return routeDirections;
        }
        for (int i = 0; i < shortestPath.size()-1; i++) {
            MetroStop src = shortestPath.get(i);
            MetroStop dst = shortestPath.get(i+1);
            Set<MetroStop> set = new HashSet<>();
            set.add(src);
            set.add(dst);
            double time = stopPairTravelTimes.get(set);

            String[] parts = src.stopDescription.split("-");
            if (parts.length == 2) {
                if(src.metroLine == dst.metroLine)
                    routeDirections.add(new RouteDirection(src.stopDescription, dst.stopDescription, time , true));
                else
                    routeDirections.add(new RouteDirection(src.stopDescription, dst.stopDescription, time, false));
            }
            else if (parts.length == 1) {
                if(src.metroLine == dst.metroLine)
                    routeDirections.add(new RouteDirection(src.stopDescription, dst.stopDescription, time , true));
                else
                    routeDirections.add(new RouteDirection(src.stopDescription, dst.stopDescription, time, false));

            }

        }

        return routeDirections;
    }

    public static void addConnectionBetweenStops(MetroStop source, MetroStop destination, double velocity) {
        double dx = source.xCoord - destination.xCoord;
        double dy = source.yCoord - destination.yCoord;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double duration = distance / velocity;

        Edge edge = new Edge(destination, duration);

        source.connections.add(edge);

        destination.connections.add(new Edge(source, duration));
    }


    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {

        System.out.println();
        System.out.println("Directions");
        System.out.println("----------");

        for (int i = 0; i < directions.size() ; i++) {
            RouteDirection currentDirection = directions.get(i);
            String description;
            if (currentDirection.trainRide) {
                description = String.format("Get on the train from \"%s\" to \"%s\" for %.2f minutes.",
                        currentDirection.startStationName, currentDirection.endStationName, currentDirection.duration);
            } else {
                description = String.format("Walk from \"%s\" to \"%s\" for %.2f minutes.",
                        currentDirection.startStationName, currentDirection.endStationName, currentDirection.duration);
            }
            System.out.println((i + 1) + ". " + description);
        }
    }


    public static double calculateShortestRoute(MetroStop startingPoint, MetroStop finalDestination)
    {
        HashMap<MetroStop, Double> shortestTimes = new HashMap<>();

        shortestTimes.put(startingPoint, 0.0);
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.offer(new Edge(startingPoint, 0.0));
        while (!queue.isEmpty()) {
            Edge currentEdge = queue.poll();
            if (currentEdge.nextMetroStop == finalDestination) break;

            for (Edge nextEdge : currentEdge.nextMetroStop.connections) {
                double totalTime = currentEdge.time + nextEdge.time;
                if (totalTime < shortestTimes.getOrDefault(nextEdge.nextMetroStop, Double.MAX_VALUE)) {
                    shortestTimes.put(nextEdge.nextMetroStop,totalTime);
                    previousStopsMap.put(nextEdge.nextMetroStop, currentEdge.nextMetroStop);
                    Set<MetroStop> stopPair = new HashSet<>();
                    stopPair.add(currentEdge.nextMetroStop);
                    stopPair.add(nextEdge.nextMetroStop);
                    stopPairTravelTimes.put(stopPair, nextEdge.time);
                    queue.offer(new Edge(nextEdge.nextMetroStop, totalTime));
                }
            }
        }
        return Math.round(shortestTimes.get(finalDestination));
    }
}
