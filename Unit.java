import java.util.*;
import java.lang.reflect.*;

public class Unit {

    public static Map<String, Throwable> testClass(String name) {
        Class<?> c = getClassFromString(name);

        TestMethods methods = new TestMethods(c);

        runMeths(c, methods.before_class);

        Map<String, Throwable> results = runTests(c, methods.tests, methods.before, methods.after);

        runMeths(c, methods.after_class);

        return results;
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
        throw new UnsupportedOperationException();
    }

    private static Class<?> getClassFromString(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            throw new BadClassException();
        }
    }

    private static Map<String, Throwable> runTests(Class<?> c, List<Method> tests, List<Method> before, List<Method> after) {
        Map<String, Throwable> results = new HashMap<>();
        
        for (Method t : tests) {
            runMeths(c, before);
            try {
                t.invoke(c, (Object[])null);
                results.put(t.getName(), null);
            } catch (InvocationTargetException ex) {
                results.put(t.getName(), ex.getCause());
            } catch (IllegalAccessException ex) {
                throw new IllegalAccessError();
            }
            runMeths(c, after);
        }

        return results;
    }

    private static void runMeths(Class<?> c, List<Method> meths) {
        for (Method m : meths) {
            try {
                m.invoke(c, (Object[])null);
            } catch (InvocationTargetException ex) {
                throw new BadMethodException();
            } catch (IllegalAccessException ex) {
                throw new BadMethodException();
            }
        }
    }
}
