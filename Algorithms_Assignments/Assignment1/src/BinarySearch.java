public class BinarySearch {
    public int Search(int[] arr, int value) {
        int lowIndex = 0;
        int highIndex = arr.length - 1;

        while (highIndex - lowIndex > 1){
            int mid = (highIndex + lowIndex) / 2;
            if(arr[mid] < value) {
                lowIndex = mid + 1;
            }
            else {
                highIndex = mid;
            }
        }

        if(arr[lowIndex] == value)
            return lowIndex;

        if(arr[highIndex] == value)
            return highIndex;

        return -1;
    }
}
