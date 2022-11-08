/* PropertyMethods.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A class that parses, maintains, and runs the Property methods of the class
 * that we are trying to test.
 */

import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.Annotation;

public class PropertyMethods {
    private List<PropertyMethod> meths = new ArrayList<>();
    private Object o;

    /* constructor
     *    Purpose: Initializes the PropertyMethods to contain a sorted list of
     *             @Property methods, the class that we are testing, and an
     *             instance of that class.
     * Parameters: A reflected class
     *    Returns: none
     *    Effects: none
     */
    public PropertyMethods(Class<?> c) {
        this.o = instantiateClass(c);

        Method all_methods[] = c.getMethods();
            for (Method m : all_methods) {
                Annotation a = m.getAnnotation(Property.class);
                if (a != null) {
                    this.meths.add(new PropertyMethod(m, c, o));
                }
            }
        Collections.sort(this.meths, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    /* run
     *    Purpose: Runs the @Property test methods contained within the reciever
     * Parameters: none
     *    Returns: A map of the test name to the argument list that caused the
     *             test to fail. If a test doesn't fail its name maps to null
     *    Effects: none
     */
    public Map<String, Object[]> run() {
        Map<String, Object[]> results = new HashMap<>();

        for (PropertyMethod m : meths) {
            results.put(m.getName(), m.run());
        }

        return results;
    }

    /* toString
     *    Purpose: A debugging helper method that returns a stringified version
     *             of the methods we have discovered
     * Parameters: none
     *    Returns: A string representation of the object
     *    Effects: none
     */
    public String toString() {
        return String.format("PropertyMethods: %s", meths.toString());
    }

    /* instantiateClass
     *    Purpose: Instantiates a class reflectively while catching exceptions
     * Parameters: A class to instantiate
     *    Returns: The object of that class.
     *    Effects: throws if something goes wrong
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
}
