/* TestMethods.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A class that parses, maintains, and runs the Test methods of the class
 * that we are trying to test.
 */

import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class TestMethods {
    private List<Method> tests = new ArrayList<>();
    private List<Method> before_class = new ArrayList<>();
    private List<Method> after_class = new ArrayList<>();
    private List<Method> before = new ArrayList<>();
    private List<Method> after = new ArrayList<>();
    private Object o;

    private enum MethodType {
        TESTS,
        BEFORE_CLASS,
        AFTER_CLASS,
        BEFORE,
        AFTER
    }

    public TestMethods(Class<?> c) {
        Method all_meths[] = c.getMethods();
        o = instantiateClass(c);
        for (Method m : all_meths) {
            Annotation as[] = m.getAnnotations();
            List<Annotation> important_as = new ArrayList<>();

            for (Annotation a : as) {
                if (isImportantAnnotation(a)) {
                    important_as.add(a);
                }
            }
            processMeth(m, important_as);
        }
        sort();
    }

    private void processMeth(Method m, List<Annotation> as) {
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

    public void runBefore() {
        runMeths(MethodType.BEFORE_CLASS);
    }

    public void runAfter() {
        runMeths(MethodType.AFTER_CLASS);
    }

    public Map<String, Throwable> runTests() {
        Map<String, Throwable> results = new HashMap<>();
        
        for (Method t : tests) {
            runMeths(MethodType.BEFORE);
            try {
                t.invoke(o, (Object[])null);
                results.put(t.getName(), null);
            } catch (InvocationTargetException ex) {
                results.put(t.getName(), ex.getCause());
            } catch (IllegalAccessException ex) {
                throw new IllegalAccessError();
            }
            runMeths(MethodType.AFTER);
        }

        return results;
    }

    private void runMeths(MethodType mt) {

        List<Method> meths;
        
        if (mt == MethodType.BEFORE_CLASS) {
            meths = before_class;
        } else if (mt == MethodType.AFTER_CLASS) {
            meths = after_class;
        } else if (mt == MethodType.BEFORE) {
            meths = before;
        } else if (mt == MethodType.AFTER) {
            meths = after;
        } else {
            meths = tests;
        }

        for (Method m : meths) {
            try {
                m.invoke(o, (Object[])null);
            } catch (InvocationTargetException ex) {
                throw new BadMethodException();
            } catch (IllegalAccessException ex) {
                throw new BadMethodException();
            }
        }
    }

    private void sort() {
        Collections.sort(tests, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(before_class, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(after_class, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(before, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(after, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    private Object instantiateClass(Class<?> c) {
        try {
            return c.getConstructor().newInstance();
        } catch (NoSuchMethodException ex) {
            throw new BadClassException();
        } catch (IllegalAccessException ex) {
            throw new BadClassException();
        } catch (InvocationTargetException ex) {
            throw new BadClassException();
        } catch (InstantiationException ex) {
            throw new BadClassException();
        }
    }
}
