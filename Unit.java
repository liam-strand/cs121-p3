/* Unit.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * Provides the functions that run the Test methods or the 
 * Property methods of a class
 */

import java.util.*;

public class Unit {

    public static Map<String, Throwable> testClass(String name) {
        Class<?> c = getClassFromString(name);
        TestMethods methods = new TestMethods(c);

        methods.runBefore();
        Map<String, Throwable> results = methods.runTests();
        methods.runAfter();

        return results;
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
        Class<?> c = getClassFromString(name);

        PropertyMethods meths = new PropertyMethods(c);
        
        return meths.run();
    }

    

    private static Class<?> getClassFromString(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            throw new BadClassException();
        }
    }
}
