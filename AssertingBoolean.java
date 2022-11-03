public class AssertingBoolean {
    private boolean b;
    public AssertingBoolean(boolean b) {
        this.b = b;
    }
    public AssertingBoolean isEqualTo(boolean b2) {
        if (b != b2) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingBoolean isTrue() {
        if (!b) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingBoolean isFalse() {
        if (b) {
            throw new AssertionException();
        }
        return this;
    }
}
