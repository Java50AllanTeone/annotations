package telran.test;

import telran.reflect.BeforeEach;
import telran.reflect.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestLibrary {
    public static void launchTest(Object testObj) throws Exception {
        Method[] methods = testObj.getClass().getDeclaredMethods();
        Method[] beMethods = getBeforeEach(methods);
        for (Method method: methods) {
            if (method.isAnnotationPresent(Test.class)) {
                runBeforeEach(beMethods, testObj);
                method.setAccessible(true);
                method.invoke(testObj);
            }

        }
    }



    public static Method[] getBeforeEach(Method[] methods) {
        return Arrays.stream(methods).
                filter(e -> e.isAnnotationPresent(BeforeEach.class))
                .toArray(Method[]::new);
    }

    public static void runBeforeEach(Method[] methods, Object arg) {
        Arrays.stream(methods).
                forEach(e -> {
                    e.setAccessible(true);
                    try {
                        e.invoke(arg);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }
}
