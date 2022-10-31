import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class TestMethods {
    public List<Method> tests = new ArrayList<>();
    public List<Method> before_class = new ArrayList<>();
    public List<Method> after_class = new ArrayList<>();
    public List<Method> before = new ArrayList<>();
    public List<Method> after = new ArrayList<>();

    public TestMethods(Class<?> c) {
        Method all_meths[] = c.getMethods();

        for (Method m : all_meths) {
            Annotation as[] = m.getAnnotations();
            List<Annotation> important_as = new ArrayList<>();

            for (Annotation a : as) {
                if (isImportantAnnotation(a)) {
                    important_as.add(a);
                }
            }
            processAnnotation(m, important_as);
        }
        sort();
    }

    private void processAnnotation(Method m, List<Annotation> as) {
        if (as.size() == 1) {
            Annotation a = as.get(0);
            if (a instanceof Test) {
                tests.add(m);
            } else if (a instanceof Before) {
                before.add(m);
            } else if (a instanceof After) {
                after.add(m);
            } else  {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new IncompatibleAnnotationsException();
                }
                if (a instanceof BeforeClass) {
                    before_class.add(m);
                } else {
                    after_class.add(m);
                }
            }
        } else if (as.size() > 1) {
            throw new IncompatibleAnnotationsException();
        }
    }
    
    private static boolean isImportantAnnotation(Annotation a) {
        boolean result = a instanceof Test;
        result |= a instanceof Before;
        result |= a instanceof After;
        result |= a instanceof BeforeClass;
        result |= a instanceof AfterClass;
        
        return result;
    }

    public void sort() {
        Collections.sort(tests, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(before_class, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(after_class, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(before, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(after, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }
}
