import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class TesterClass {
    public static void start(Class testingClass) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Method> methodsToTest = new ArrayList<>();
        Method[] declaredMethods = testingClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Test.class)) methodsToTest.add(declaredMethod);
        }

        methodsToTest.sort(Comparator.comparingInt((Method m) -> m.getAnnotation(Test.class).priority()).reversed());
        //  подсмотрел в 8-м уроке, делал через циклы

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (methodsToTest.size() > 0 && methodsToTest.get(0).isAnnotationPresent(BeforeSuite.class))
                    throw new RuntimeException("BeforeSuite method err");
                else methodsToTest.add(0, declaredMethod);
            }

            if (declaredMethod.isAnnotationPresent(AfterSuite.class)) {
                if (methodsToTest.size() > 0 && methodsToTest.get(methodsToTest.size() - 1).isAnnotationPresent(AfterSuite.class))
                    throw new RuntimeException("AfterSuite method err");
                else methodsToTest.add(declaredMethod);
            }
        }

        for (Method methodToTest : methodsToTest){
            methodToTest.invoke(null);
        }
    }

}
