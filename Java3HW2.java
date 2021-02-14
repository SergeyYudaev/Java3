import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.Arrays;

public class Java3HW2 {
    public static void main(String[] args) {

        String[] arr = new String[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(i);
        }
        // задание 1
        changeArrElem(arr, 3, 9);

        // задание 2
        arrayToList(arr);

        // задание 3
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i = 0; i < 10; i++) {
            appleBox.add(new Apple());
            orangeBox.add(new Orange());
        }

        Box<Apple> appleBox1 = new Box<>();

        appleBox.move(appleBox1);


        System.out.println("appleBox weight: " + appleBox.getBoxWieght());
        System.out.println("orangeBox weight: " + orangeBox.getBoxWieght());
        System.out.println(appleBox.compare(orangeBox));
        System.out.println("appleBox1 weight: " + appleBox1.getBoxWieght());


    }

    public static String[] changeArrElem(String[] arr, int elem1, int elem2) {
        try {
            String tmp = arr[elem1];
            arr[elem1] = arr[elem2];
            arr[elem2] = tmp;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<String> arrayToList(String[] arr) {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
        return arrayList;
    }


}
