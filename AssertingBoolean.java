/* AssertingBoolean.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * An object that wraps around a boolean and enables fluent assertions
 */

public class AssertingBoolean {
    private boolean b;
    public AssertingBoolean(boolean b) {
        this.b = b;
    }

    /* isEqualTo
     *    Purpose: Throws a pretty exception if this.b has the wrong value
     * Parameters: A boolean to compare to
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingBoolean isEqualTo(boolean b2) {
        if (b != b2) {
            throw new AssertionException(String.format("b != b2 because b = %s and b2 = %s", b ? "true" : "false", b2 ? "true" : "false"));
        }
        return this;
    }

    /* isTrue
     *    Purpose: Throws a pretty exception if this.b is false
     * Parameters: none
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingBoolean isTrue() {
        if (!b) {
            throw new AssertionException("b should be true, is false");
        }
        return this;
    }

    /* isFalse
     *    Purpose: Throws a pretty exception if this.b is true
     * Parameters: none
     *    Returns: this, to allow fluent method chaining
     *    Effects: Throws if the assertion is not met
     */
    public AssertingBoolean isFalse() {
        if (b) {
            throw new AssertionException("b should be false, is true");
        }
        return this;
    }
}
