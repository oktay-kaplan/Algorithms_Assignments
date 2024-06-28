import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");

        ArrayList<Integer> demandsList = new ArrayList<>();
        String inputFile1 = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile1))) {
            String[] demandSchedule = br.readLine().split(" ");
            for (String demand : demandSchedule) {
                demandsList.add(Integer.parseInt(demand));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        PowerGridOptimization gridOptimization = new PowerGridOptimization(demandsList);

        OptimalPowerGridSolution optimalSolution = gridOptimization.getOptimalPowerGridSolutionDP();

        System.out.println("The total number of demanded gigawatts: " + gridOptimization.calculateTotalDemandedGigawatts());
        System.out.println("Maximum number of satisfied gigawatts: " + optimalSolution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        ArrayList<Integer> hours = optimalSolution.getHoursToDischargeBatteriesForMaxEfficiency();
        for (int i = 0; i < hours.size(); i++) {
            System.out.print(hours.get(i));
            if (i != hours.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("The number of unsatisfied gigawatts: " + (gridOptimization.calculateTotalDemandedGigawatts() - optimalSolution.getmaxNumberOfSatisfiedDemands()));
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");









        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");

        String inputFile2 = args[1];

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile2))) {
            String[] maintenanceData = br.readLine().split(" ");
            int availableESVCount = Integer.parseInt(maintenanceData[0]);
            int ESVCapacity = Integer.parseInt(maintenanceData[1]);

            ArrayList<Integer> energyRequirements = new ArrayList<>();
            String[] taskDemands = br.readLine().split(" ");
            for (String demand : taskDemands) {
                energyRequirements.add(Integer.parseInt(demand));
            }

            OptimalESVDeploymentGP optimalESVDeployment = new OptimalESVDeploymentGP(energyRequirements);

            int minESVsToDeploy = optimalESVDeployment.getMinNumESVsToDeploy(availableESVCount, ESVCapacity);

            if (minESVsToDeploy != -1) {
                System.out.println("The minimum number of ESVs to deploy: " + minESVsToDeploy);
                ArrayList<ArrayList<Integer>> tasksAssignedToESVs = optimalESVDeployment.getMaintenanceTasksAssignedToESVs();
                for (int i = 0; i < tasksAssignedToESVs.size(); i++) {
                    System.out.println("ESV " + (i + 1) + " tasks: " + tasksAssignedToESVs.get(i));
                }
            } else {
                System.out.println("Warning: Mission Eco-Maintenance Failed.");
            }

            System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}