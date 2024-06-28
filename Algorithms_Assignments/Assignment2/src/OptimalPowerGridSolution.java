import java.util.ArrayList;

public class OptimalPowerGridSolution {
    private int maxNumberOfSatisfiedDemands;
    private ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency;

    public OptimalPowerGridSolution(int maxNumberOfSatisfiedDemands, ArrayList<Integer> hoursToDischargeBatteriesForMaxEfficiency) {
        this.maxNumberOfSatisfiedDemands = maxNumberOfSatisfiedDemands;
        this.hoursToDischargeBatteriesForMaxEfficiency = hoursToDischargeBatteriesForMaxEfficiency;
    }

    public int getmaxNumberOfSatisfiedDemands() {
        return maxNumberOfSatisfiedDemands;
    }

    public ArrayList<Integer> getHoursToDischargeBatteriesForMaxEfficiency() {
        return hoursToDischargeBatteriesForMaxEfficiency;
    }
}