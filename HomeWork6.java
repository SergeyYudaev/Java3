import java.util.Arrays;

public class HomeWork6 {

    public static void main(String[] args) {


    }

    public static int[] arrCutter(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException();
    }

    public static boolean oneAndFourArrChecker(int[] arr) {
        boolean flag1 = false;
        boolean flag2 = false;
        for (int i = 0; i < arr.length; i++) {
            if (!flag1 || !flag2) {
                if (arr[i] != 1 && arr[i] != 4) return false;
                if (arr[i] == 1) flag1 = true;
                else flag2 = true;
            }
        }
        return flag1 && flag2;
    }
}

