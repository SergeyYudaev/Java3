import org.junit.Assert;
import org.junit.Test;

public class HomeWork6Test {

    @Test
    public void arrayCutterTest() {
        int[] arr = {1, 2, 3, 5, 4, 6, 7};
        int[] expectedArr = {6, 7};
        int[] result = HomeWork6.arrCutter(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test(expected = RuntimeException.class)
    public void arrayCutterTest1() {
        int[] arr = {1, 2, 3, 5, 6, 7};
        int[] expectedArr = {};
        int[] result = HomeWork6.arrCutter(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void arrayCutterTest2() {
        int[] arr = {4, 2, 3, 5, 6, 7};
        int[] expectedArr = {2, 3, 5, 6, 7};
        int[] result = HomeWork6.arrCutter(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void arrayCutterTest3() {
        int[] arr = {1, 2, 3, 5, 6, 4};
        int[] expectedArr = {};
        int[] result = HomeWork6.arrCutter(arr);
        Assert.assertArrayEquals(expectedArr, result);
    }

    @Test
    public void OneAndFourArrChecker() {
        int[] arr = {1, 1, 1, 1};
        boolean result = HomeWork6.oneAndFourArrChecker(arr);
        Assert.assertEquals(false, result);
    }

    @Test
    public void OneAndFourArrChecker1() {
        int[] arr = {4, 4, 4, 4};
        boolean result = HomeWork6.oneAndFourArrChecker(arr);
        Assert.assertEquals(false, result);
    }

    @Test
    public void OneAndFourArrChecker2() {
        int[] arr = {2, 3, 0, 5};
        boolean result = HomeWork6.oneAndFourArrChecker(arr);
        Assert.assertEquals(false, result);
    }

    @Test
    public void OneAndFourArrChecker3() {
        int[] arr = {1, 4, 1, 4};
        boolean result = HomeWork6.oneAndFourArrChecker(arr);
        Assert.assertEquals(true, result);
    }

}
