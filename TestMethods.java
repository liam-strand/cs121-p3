/* TestMethods.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A class that parses, maintains, and runs the Test methods of the class
 * that we are trying to test.
 */

import java.util.*;
import java.util.function.BiFunction;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class TestMethods {
    private List<TestMethod> tests = new ArrayList<>();
    private List<UtilMethod> before_class = new ArrayList<>();
    private List<UtilMethod> after_class = new ArrayList<>();
    private List<UtilMethod> before = new ArrayList<>();
    private List<UtilMethod> after = new ArrayList<>();
    private Object o;

    private enum MethodType {
        TESTS,
        BEFORE_CLASS,
        AFTER_CLASS,
        BEFORE,
        AFTER
    }

    /* constructor
     *    Purpose: Instantiates an object containing all the testing and
     *             associated methods in the provided class
     * Parameters: A class name
     */
    public TestMethods(String name) {
        this(getClassFromString(name));
    }

    /* constructor
     *    Purpose: Instantiates an object containing all the testing and
     *             associated methods in the provided class
     * Parameters: A reflected class object
     */
    public TestMethods(Class<?> c) {
        Method all_meths[] = c.getMethods();
        o = instantiateClass(c);
        for (Method m : all_meths) {
            processMeth(m);
        }
        sort();
    }

    /* processMeth
     *    Purpose: Takes a method and its annotations and puts it in the  
     *             appropriate category
     * Parameters: A Method to process
     *    Returns: none
     *    Effects: Adds to the method lists
     */
    private void processMeth(Method m) {
        Annotation as[] = m.getAnnotations();
        List<Annotation> important_as = new ArrayList<>();

        for (Annotation a : as) {
            if (isImportantAnnotation(a)) {
                important_as.add(a);
            }
        }
        if (important_as.size() == 1) {
            Annotation a = as[0];
            if (a instanceof Test) {
                tests.add(new TestMethod(m, o));
            } else if (a instanceof Before) {
                before.add(new UtilMethod(m, o));
            } else if (a instanceof After) {
                after.add(new UtilMethod(m, o));
            } else  {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new IncompatibleAnnotationsException();
                }
                if (a instanceof BeforeClass) {
                    before_class.add(new UtilMethod(m, o));
                } else {
                    after_class.add(new UtilMethod(m, o));
                }
            }
        } else if (important_as.size() > 1) {
            throw new IncompatibleAnnotationsException();
        }
    }
    
    /* isImportantAnnotation
     *    Purpose: Checks to see if a given annotation is one of the 
     *             annotations we care about.
     * Parameters: An annotation to examine
     *    Returns: True if we care, false if not.
     *    Effects: none
     */
    private static boolean isImportantAnnotation(Annotation a) {
        boolean result = a instanceof Test;
        result |= a instanceof Before;
        result |= a instanceof After;
        result |= a instanceof BeforeClass;
        result |= a instanceof AfterClass;
        
        return result;
    }

    /* runBefore
     *    Purpose: Runs the BeforeClass methods
     * Parameters: none
     *    Returns: none
     *    Effects: none
     */
    public void runBefore() {
        runMeths(MethodType.BEFORE_CLASS);
    }

    /* runAfter
     *    Purpose: Runs the AfterClass methods
     * Parameters: none
     *    Returns: none
     *    Effects: none
     */
    public void runAfter() {
        runMeths(MethodType.AFTER_CLASS);
    }

    /* runTests
     *    Purpose: Runs the tests, along with the before/after methods
     * Parameters: none
     *    Returns: A map of each test name to null if the test passed or the
     *             thrown exception/error that caused the test to fail.
     *    Effects: none
     */
    public Map<String, Throwable> runTests() {
        Map<String, Throwable> results = new HashMap<>();
        
        for (TestMethod t : tests) {
            runMeths(MethodType.BEFORE);

            results.put(t.getName(), t.run());

            
            runMeths(MethodType.AFTER);
        }

        return results;
    }

    /* runMeths
     *    Purpose: Runs a specified method list
     * Parameters: An indicator of what method type to run
     *    Returns: none
     *    Effects: none
     */
    private void runMeths(MethodType mt) {
        List<UtilMethod> meths;
        
        if (mt == MethodType.BEFORE_CLASS) {
            meths = before_class;
        } else if (mt == MethodType.AFTER_CLASS) {
            meths = after_class;
        } else if (mt == MethodType.BEFORE) {
            meths = before;
        } else if (mt == MethodType.AFTER) {
            meths = after;
        } else {
            for(TestMethod t : tests) {
                t.run();
            }
            meths = null;
        }

        for (UtilMethod m : meths) {
            m.run();
        }
    }

    /* sort
     *    Purpose: Sorts the method lists
     * Parameters: none
     *    Returns: none
     *    Effects: Sorts the method lists in-place
     */
    private void sort() {
        BiFunction<UtilMethod, UtilMethod, Integer> comparitor = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Collections.sort(tests,        comparitor::apply);
        Collections.sort(before_class, comparitor::apply);
        Collections.sort(after_class,  comparitor::apply);
        Collections.sort(before,       comparitor::apply);
        Collections.sort(after,        comparitor::apply);
    }

    /* instantiateClass
     *    Purpose: Creates an object from a reflected class
     * Parameters: A reflected class
     *    Returns: A default object of that class.
     *    Effects: none
     */
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

    /* getClassFromString
     *    Purpose: Safely gets a class from a string name
     * Parameters: The name of a class
     *    Returns: The reflected class object
     *    Effects: none
     */
    private static Class<?> getClassFromString(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            throw new BadClassException();
        }
    }
}
