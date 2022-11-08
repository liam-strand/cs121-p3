/* Unit.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * Provides the functions that run the Test methods or the 
 * Property methods of a class.
 */

import java.util.*;

public class Unit {

    /* testClass
     *    Purpose: Runs all of the tests in the provided class
     * Parameters: The name of a class to test
     *    Returns: A map of each test name to null if the test passed or the
     *             thrown exception/error that caused the test to fail.
     *    Effects: none
     */
    public static Map<String, Throwable> testClass(String name) {
        Class<?> c = getClassFromString(name);
        TestMethods methods = new TestMethods(c);

        methods.runBefore();
        Map<String, Throwable> results = methods.runTests();
        methods.runAfter();

        return results;
    }

    /* quickCheckClass
     *    Purpose: Runs the @Property test methods in the provided class
     * Parameters: The name of a class to test
     *    Returns: A map of the test name to the argument list that caused the
     *             test to fail. If a test doesn't fail its name maps to null
     *    Effects: none
     */
    public static Map<String, Object[]> quickCheckClass(String name) {
        Class<?> c = getClassFromString(name);

        PropertyMethods meths = new PropertyMethods(c);
        
        return meths.run();
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
