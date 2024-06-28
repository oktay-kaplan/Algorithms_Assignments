import java.util.Arrays;

public class CountingSort{
    //public long count;
    public void sort(int[] arr) {
        countingSort(arr, Arrays.stream(arr).max().orElse(0));
    }

    public static int[] countingSort(int[] A, int k) {
        int[] count = new int[(k + 1)];
        int[] output = new int[A.length];
        int size = A.length;

        for (int i = 0; i < size; i++) {
            count[A[i]]++;
        }

        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            output[(count[ A[i]] - 1)] = A[i];
            count[ A[i]]--;
        }

        return output;
    }

    public String getName() {
        return "Counting Sort";
    }
}
