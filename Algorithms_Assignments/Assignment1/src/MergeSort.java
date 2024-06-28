import java.util.Arrays;

public class MergeSort {
    public void sort(int[] arr) {
        mergeSort(arr);
    }


    public static int[] mergeSort(int[] A) {
        int n = A.length;
        if (n <= 1) {
            return A;
        }

        int mid = n / 2;
        int[] left = Arrays.copyOfRange(A, 0, mid);
        int[] right = Arrays.copyOfRange(A, mid, n);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        int i = 0, j = 0, k = 0;

        while (i < A.length && j < B.length) {
            if (A[i] <= B[j]) {
                C[k] = A[i];
                i++;
            } else {
                C[k] = B[j];
                j++;
            }
            k++;
        }

        while (i < A.length) {
            C[k] = A[i];
            i++;
            k++;
        }

        while (j < B.length) {
            C[k] = B[j];
            j++;
            k++;
        }

        return C;
    }


    public String getName() {
        return "Merge Sort";
    }


}
