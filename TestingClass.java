public class TestingClass {


    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("beforeSuite()");
    }

//    @BeforeSuite
//    public static void beforeSuite1() {
//        System.out.println("beforeSuite()");
//    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println("1 priority");
    }
    @Test
    public static void test2() {
        System.out.println("default priority");
    }

    @Test(priority = 10)
    public static void test3() {
        System.out.println("10 priority");
    }

    @Test(priority = 10)
    public static void test4() {
        System.out.println("10 priority");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("afterSuite()");
    }

//    @AfterSuite
//    public static void afterSuite1() {
//        System.out.println("afterSuite()");
//    }

}
