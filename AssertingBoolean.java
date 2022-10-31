public class AssertingBoolean {
    private boolean b;
    public AssertingBoolean(boolean b) {
        this.b = b;
    }
    public AssertingBoolean isEqualTo(boolean b2) {
        if (b != b2) {
            throw new AssertionError(b ? "b != b2, b is true and b2 is false" : "b != b2, b is false and b2 is true");
        }
        return this;
    }
    public AssertingBoolean isTrue() {
        if (!b) {
            throw new AssertionError("is false");
        }
        return this;
    }
    public AssertingBoolean isFalse() {
        if (b) {
            throw new AssertionError("is true");
        }
        return this;
    }
}
