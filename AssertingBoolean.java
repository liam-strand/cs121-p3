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
    public AssertingBoolean isEqualTo(boolean b2) {
        if (b != b2) {
            throw new AssertionException(String.format("b != b2 because b = %s and b2 = %s", b ? "true" : "false", b2 ? "true" : "false"));
        }
        return this;
    }
    public AssertingBoolean isTrue() {
        if (!b) {
            throw new AssertionException("b should be true, is false");
        }
        return this;
    }
    public AssertingBoolean isFalse() {
        if (b) {
            throw new AssertionException("b should be false, is true");
        }
        return this;
    }
}
