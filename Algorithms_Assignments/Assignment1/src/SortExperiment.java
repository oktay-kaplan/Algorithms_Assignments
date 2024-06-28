public class SortExperiment {

    private final InsertionSort insertionSort;
    private final MergeSort mergeSort;

    private final CountingSort countingSort;

    private final int[] inputSizes;

    public SortExperiment(int[] inputSizes, InsertionSort insertionSort,MergeSort mergeSort,CountingSort countingSort) {
        this.insertionSort = insertionSort;
        this.mergeSort = mergeSort;
        this.countingSort = countingSort;
        this.inputSizes = inputSizes;
    }

    public void runExperiment(String title, int[][] arr){
        Chart chart = new Chart(title, inputSizes, "Input sizes", "Time in milliseconds");
        chart.addData(insertionSort.getName(),getTimingResultsInsertion(arr,insertionSort));
        chart.addData(mergeSort.getName(),getTimingResultsMerge(arr,mergeSort));
        chart.addData(countingSort.getName(),getTimingResultsCounting(arr,countingSort));

        chart.show();
        chart.save();
    }

    private static double[] getTimingResultsInsertion(int[][] arrParam, InsertionSort insertionSort){
        int[][] arrays = arrParam.clone();
        double[] timesArr = new double[arrays.length];
        for (int index = 0; index< arrays.length; index++) {
            double sum = 0;
            for (int i = 0; i < 10; i++) {
                int[] arr = (arrays[index]).clone();
                long t1 = System.currentTimeMillis();
                insertionSort.sort(arr);
                sum += System.currentTimeMillis()-t1;
            }
            sum = sum/10;
            timesArr[index] = sum;
            System.out.println(insertionSort.getName() + "\t" + arrays[index].length + "\t TIME: " + sum);
        }
        return timesArr;
    }
    private static double[] getTimingResultsMerge(int[][] arrParam, MergeSort mergeSort){
        int[][] arrays = arrParam.clone();
        double[] timesArr = new double[arrays.length];
        for (int index = 0; index< arrays.length; index++) {
            double sum = 0;
            for (int i = 0; i < 10; i++) {
                int[] arr = (arrays[index]).clone();
                long t1 = System.currentTimeMillis();
                mergeSort.sort(arr);
                sum += System.currentTimeMillis()-t1;
            }
            sum = sum/10;
            timesArr[index] = sum;
            System.out.println(mergeSort.getName() + "\t" + arrays[index].length + "\t TIME: " + sum);
        }
        return timesArr;
    }
    private static double[] getTimingResultsCounting(int[][] arrParam, CountingSort countingSort){
        int[][] arrays = arrParam.clone();
        double[] timesArr = new double[arrays.length];
        for (int index = 0; index< arrays.length; index++) {
            double sum = 0;
            for (int i = 0; i < 10; i++) {
                int[] arr = (arrays[index]).clone();
                long t1 = System.currentTimeMillis();
                countingSort.sort(arr);
                sum += System.currentTimeMillis()-t1;
            }
            sum = sum/10;
            timesArr[index] = sum;
            System.out.println(countingSort.getName() + "\t" + arrays[index].length + "\t TIME: " + sum);
        }
        return timesArr;
    }
}
