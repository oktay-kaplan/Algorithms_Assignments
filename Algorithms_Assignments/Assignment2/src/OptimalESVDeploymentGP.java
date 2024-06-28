import java.util.ArrayList;
import java.util.Collections;

public class OptimalESVDeploymentGP {
    private ArrayList<Integer> maintenanceTaskEnergyDemands;
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) {
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());

        ArrayList<Integer> remainingCapacities = new ArrayList<>();
        for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
            remainingCapacities.add(maxESVCapacity);
        }

        for (int task : maintenanceTaskEnergyDemands) {
            boolean isTaskAssigned = false;
            for (int i = 0; i < remainingCapacities.size(); i++) {
                if (task <= remainingCapacities.get(i)) {
                    if (maintenanceTasksAssignedToESVs.size() <= i) {
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    }
                    maintenanceTasksAssignedToESVs.get(i).add(task);
                    remainingCapacities.set(i, remainingCapacities.get(i) - task);
                    isTaskAssigned = true;
                    break;
                }
            }
            if (!isTaskAssigned) {
                return -1;
            }
        }

        return maintenanceTasksAssignedToESVs.size();
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }


    public ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }
}