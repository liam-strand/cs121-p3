/* AssertingObject.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A wrapper around an Object that enables fluent assertions.
 */

public class AssertingObject {
    private Object o;
    public AssertingObject(Object o) {
        this.o = o;
    }

    /* isNotNull
     *    Purpose: Throws a pretty exception if this.o is null
     * Parameters: none
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingObject isNotNull() {
        if (o == null) {
            throw new AssertionException("o should not be null, but it is null");
        }
        return this;
    }

    /* isNull
     *    Purpose: Throws a pretty exception if this.o is not null
     * Parameters: none
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingObject isNull() {
        if (o != null) {
            throw new AssertionException(String.format("o should be null, but it is %s", o.toString()));
        }
        return this;
    }

    /* isEqualTo
     *    Purpose: Throws a pretty exception if this.o is not .equals the argument
     * Parameters: An Object to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingObject isEqualTo(Object o2) {
        if (!o.equals(o2)) {
            throw new AssertionException(String.format("o != o2 because o = %s and o2 = %s", o.toString(), o2.toString()));
        }
        return this;
    }

    /* isNotEqualTo
     *    Purpose: Throws a pretty exception if this.o .equals the argument
     * Parameters: An Object to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingObject isNotEqualTo(Object o2) {
        if (o.equals(o2)) {
            throw new AssertionException(String.format("o == o2 because o == o2 == %s", o.toString()));
        }
        return this;
    }

    /* isInstanceOf
     *    Purpose: Throws a pretty exception if this.o is not an instance of the argument
     * Parameters: An Class to compare check membership of
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(o)) {
            throw new AssertionException(String.format("o is not an instance of c because o is of type %s and c is %s", o.getClass().toString(), c.toString()));
        }
        return this;
    }
}
