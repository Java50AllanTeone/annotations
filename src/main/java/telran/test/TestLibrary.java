package telran.test;

import telran.reflect.Test;

import java.lang.reflect.Method;

public class TestLibrary {
    public static void launchTest(Object testObj) throws Exception {
        Method[] methods = testObj.getClass().getDeclaredMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(Test.class)) {
                method.setAccessible(true);
                method.invoke(testObj);
            }

        }
    }
}
