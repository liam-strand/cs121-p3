/* UtilMethod.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A wrapper around a reflected testing Method
 */

import java.lang.reflect.*;

public class TestMethod extends UtilMethod {
    public TestMethod(Method m, Object o) {
        super(m, o);
    }

    /* run
     *    Purpose: Runs the test method
     * Parameters: none
     *    Returns: null if test passed, the exception that caused the failure
     *             otherwise 
     *    Effects: none
     */
    @Override
    public Throwable run() {
        try {
            m.invoke(o, (Object[])null);
            return null;
        } catch (InvocationTargetException ex) {
            return ex.getCause();
        } catch (IllegalAccessException ex) {
            throw new IllegalAccessError();
        }
    }
}
