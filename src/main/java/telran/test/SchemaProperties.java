package telran.test;

import telran.reflect.Id;
import telran.reflect.Index;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SchemaProperties {

    public static void displayFieldProperties(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] id = getIdFields(fields);

        if (id.length != 1) {
            throw new IllegalStateException(id.length == 0 ? "No field Id found" : "Field id must be one");
        } else {
            System.out.println("@idField: " + id[0].getName());
        }
        Arrays.stream(getIndexFields(fields))
                .forEach(e -> System.out.println("@indexField: " + e.getName()));
    }

    public static Field[] getIdFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(e -> e.isAnnotationPresent(Id.class))
                .toArray(Field[]::new);
    }

    public static Field[] getIndexFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(e -> e.isAnnotationPresent(Index.class))
                .toArray(Field[]::new);
    }
}
