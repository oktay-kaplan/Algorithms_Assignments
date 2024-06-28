import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperimentRun {
    private final int[] inputSizes;
    private final List<Integer> values = new ArrayList<>();
    String dataFile;


    public ExperimentRun(int[] inputSizes, String dataFile) {
        this.inputSizes = inputSizes;
        this.dataFile = dataFile;
    }

    public void run(){
        readCSVFile(dataFile);

        InsertionSort insertionSort = new InsertionSort();
        MergeSort mergeSort = new MergeSort();
        CountingSort countingSort = new CountingSort();
        LinearSearch linearSearch = new LinearSearch();
        BinarySearch binarySearch = new BinarySearch();

        SortExperiment classicExperiment = new SortExperiment(inputSizes, insertionSort, mergeSort, countingSort);
        SearchExperiment searchExperiment = new SearchExperiment(inputSizes, linearSearch, binarySearch);

        int[][] randomArrays = generateRandomArr();
        int[][] sortedArrays = generateSortedArr();
        int[][] reverselySortedArrays = generateReverselySortedArr();

        System.out.println("SORTING EXPERIMENT on Random Data\n");
        classicExperiment.runExperiment("Test on Random Data", randomArrays);

        System.out.println("\nSORTING EXPERIMENT on Sorted Data\n");
        classicExperiment.runExperiment("Test on Sorted Data", sortedArrays);

        System.out.println("\nSORTING EXPERIMENT on Reversely Sorted Data\n");
        classicExperiment.runExperiment("Test on Reversely Sorted Data", reverselySortedArrays);

        searchExperiment.runExperiment("Test Search Algorithms", randomArrays, sortedArrays);


    }

    private void readCSVFile(String csvFile){
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                values.add(Integer.parseInt(data[6]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] generateRandomArr(){
        int[][] arr = new int[inputSizes.length][];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getArray(inputSizes[i]);
        }
        return arr;
    }

    private int[][] generateSortedArr(){
        int[][] arr = new int[inputSizes.length][];
        for (int i = 0; i < arr.length; i++) {
            int[] sorted_arr = getArray(inputSizes[i]);
            Arrays.sort(sorted_arr);
            arr[i] = sorted_arr;
        }
        return arr;
    }

    private int[][] generateReverselySortedArr(){
        int[][] arr = new int[inputSizes.length][];
        for (int i = 0; i < arr.length; i++) {
            int[] reversely_sorted_arr = getArray(inputSizes[i]);
            Arrays.sort(reversely_sorted_arr);
            int n = reversely_sorted_arr.length;
            for (int j = 0; j < n / 2; j++) {
                int temp = reversely_sorted_arr[j];
                reversely_sorted_arr[j] = reversely_sorted_arr[n - 1 - j];
                reversely_sorted_arr[n - 1 - j] = temp;
            }
            arr[i] = reversely_sorted_arr;
        }
        return arr;
    }

    private int[] getArray(int size){
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = values.get(i);
        }
        return array;
    }
}
