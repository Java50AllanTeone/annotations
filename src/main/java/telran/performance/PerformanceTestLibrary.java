package telran.performance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class PerformanceTestLibrary {

    public static void runTests(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        runBeforeAlMethods(clazz);
        Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(PerformanceTest.class)).
                forEach(m -> {
                    try {
                        runPerformanceTest(m, obj);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    private static void runPerformanceTest(Method m, Object obj) throws Exception {
        String title = getTitle(m);
        Instant start = Instant.now();

        PerformanceTest performanceTest = m.getAnnotation(PerformanceTest.class);
        int nRuns = performanceTest.nRuns();
        
        for (int i = 0; i < nRuns; i++) {
            m.invoke(obj);
        }
        System.out.printf("test %s, running time: %d\n", title, ChronoUnit.MILLIS.between(start, Instant.now()));

    }

    private static String getTitle(Method m) {
        return m.isAnnotationPresent(DisplayName.class) ? m.getAnnotation(DisplayName.class).name()
                : m.getName();
    }

    private static void runBeforeAlMethods(Class<?> clazz) throws Exception {
        for(Method method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeAll.class)) {
                method.setAccessible(true);
                method.invoke(null);
            }

        }
    }
}
