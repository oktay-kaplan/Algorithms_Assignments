import java.util.Random;

public class SearchExperiment {
    private final LinearSearch linearSearch;
    private final BinarySearch binarySearch;
    private final int[] inputSizes;

    public SearchExperiment(int[] inputSizes, LinearSearch linearSearch, BinarySearch binarySearch) {
        this.linearSearch = linearSearch;
        this.binarySearch = binarySearch;
        this.inputSizes = inputSizes;
    }

    public void runExperiment(String title, int[][] randomArr, int[][] sortedArr){
        System.out.println("\n\nBINARY SEARCH (SORTED): ");
        double[] binarySearchSortedResults = getTimingResultsBinary(sortedArr, binarySearch);

        System.out.println("\n\nLINEAR SEARCH (SORTED): ");
        double[] linearSearchSortedResults = getTimingResultsLinear(sortedArr, linearSearch);

        System.out.println("\n\nLINEAR SEARCH (RANDOM): ");
        double[] linearSearchRandomResults = getTimingResultsLinear(randomArr, linearSearch);

        Chart chart = new Chart(title, inputSizes, "Input sizes", "Time in nanoseconds");
        chart.addData("Linear search (random data)", linearSearchRandomResults);
        chart.addData("Linear search (sorted data)", linearSearchSortedResults);
        chart.addData("Binary search (sorted data)", binarySearchSortedResults);
        chart.show();
        chart.save();
    }

    private static double[] getTimingResultsLinear(int[][] arrParam, LinearSearch linearSearch){
        int[][] experimentArrays = arrParam.clone();
        double[] timesArr = new double[experimentArrays.length];

        for (int arrIndex = 0; arrIndex< experimentArrays.length; arrIndex++) {
            double sum = 0;
            for (int i = 0; i < 1000; i++) {
                int[] arr = (experimentArrays[arrIndex]).clone();
                Random random = new Random();
                int index = random.nextInt(arr.length);
                int search_value = arr[index];
                long t1 = System.nanoTime();
                int test = linearSearch.Search(arr, search_value);
                sum += System.nanoTime()-t1;
            }
            sum = sum/1000;
            timesArr[arrIndex] = sum;
            System.out.println(experimentArrays[arrIndex].length + "\t TIME:" + sum);
        }
        return timesArr;
    }

    private static double[] getTimingResultsBinary(int[][] arrParam, BinarySearch binarySearch){
        int[][] experimentArrays = arrParam.clone();
        double[] timesArr = new double[experimentArrays.length];

        for (int arrIndex = 0; arrIndex< experimentArrays.length; arrIndex++) {
            double sum = 0;
            for (int i = 0; i < 1000; i++) {
                int[] arr = (experimentArrays[arrIndex]).clone();
                Random random = new Random();
                int index = random.nextInt(arr.length);
                int search_value = arr[index];
                long t1 = System.nanoTime();
                int test = binarySearch.Search(arr, search_value);
                sum += System.nanoTime()-t1;
            }
            sum = sum/1000;
            timesArr[arrIndex] = sum;
            System.out.println(experimentArrays[arrIndex].length + "\t TIME:" + sum);
        }
        return timesArr;
    }
}
