public class LinearSearch {
    public int Search(int[] arr, int value) {
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
