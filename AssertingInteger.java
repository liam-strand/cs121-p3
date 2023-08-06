/* AssertingInteger.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A wrapper around an Integer that enables fluent assertions.
 */

public class AssertingInteger {
    private int i;
    public AssertingInteger(int i) {
        this.i = i;
    }

    /* isEqualTo
     *    Purpose: Throws a pretty exception if this.i has the wrong value
     * Parameters: An int to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingInteger isEqualTo(int i2) {
        if (i != i2) {
            throw new AssertionException(String.format("i != i2 because i = %d and i2 = %d", i, i2));
        }
        return this;
    }

    /* isLessThan
     *    Purpose: Throws a pretty exception if this.i is too large
     * Parameters: An int to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingInteger isLessThan(int i2) {
        if (i >= i2) {
            throw new AssertionException(String.format("i >= i2 because i = %d and i2 = %d", i, i2));
        }
        return this;
    }

    /* isGreaterThan
     *    Purpose: Throws a pretty exception if this.i is too small
     * Parameters: An int to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingInteger isGreaterThan(int i2) {
        if (i <= i2) {
            throw new AssertionException(String.format("i <= i2 because i = %d and i2 = %d", i, i2));
        }
        return this;
    }
}
