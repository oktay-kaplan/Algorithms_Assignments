import java.util.ArrayList;
import java.util.List;

public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }

    public int calculateTotalDemandedGigawatts() {
        List<Integer> demandsList = new ArrayList<>(amountOfEnergyDemandsArrivingPerHour);
        return demandsList.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>();
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] SOL = new int[N + 1];

        SOL[0] = 0;
        HOURS.add(new ArrayList<>());

        for (int j = 1; j <= N; j++) {
            int max = Integer.MIN_VALUE;
            int optimalIndex = 0;
            for (int i = 0; i < j; i++) {
                int minDemandAndEfficiencyValue = Math.min(amountOfEnergyDemandsArrivingPerHour.get(j - 1), (j - i) * (j - i));
                int curr = SOL[i] + minDemandAndEfficiencyValue;
                if (curr > max) {
                    max = curr;
                    optimalIndex = i;
                }
            }
            SOL[j] = max;

            ArrayList<Integer> optimalHours = new ArrayList<>(HOURS.get(optimalIndex));
            optimalHours.add(j);
            HOURS.add(optimalHours);
        }

        return new OptimalPowerGridSolution(SOL[N], HOURS.get(N));
    }
}