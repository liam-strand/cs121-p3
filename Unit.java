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
        TestMethods methods = new TestMethods(name);

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
        PropertyMethods meths = new PropertyMethods(name);
        
        return meths.run();
    }
}
