/* UtilMethod.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A wrapper around a reflected Method
 */

import java.lang.reflect.*;

public class UtilMethod {
    protected Method m;
    protected Object o;

    public UtilMethod(Method m, Object o) {
        this.m = m;
        this.o = o;
    }

    /* getName
     *    Purpose: Gets the name of the method
     * Parameters: none
     *    Returns: The name of the method
     *    Effects: none
     */
    public String getName() {
        return m.getName();
    }

    /* run
     *    Purpose: Runs the test method
     * Parameters: none
     *    Returns: null
     *    Effects: none
     */
    public Throwable run() {
        try {
            m.invoke(o, (Object[])null);
        } catch (InvocationTargetException ex) {
            throw new BadMethodException();
        } catch (IllegalAccessException ex) {
            throw new BadMethodException();
        }
        return null;
    }

}
