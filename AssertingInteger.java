public class AssertingInteger {
    private int i;
    public AssertingInteger(int i) {
        this.i = i;
    }
    public AssertingInteger isEqualTo(int i2) {
        if (i != i2) {
            throw new AssertionError(String.format("%d != %d", i, i2));
        }
        return this;
    }
    public AssertingInteger isLessThan(int i2) {
        if (i >= i2) {
            throw new AssertionError(String.format("%d >= %d", i, i2));
        }
        return this;
    }
    public AssertingInteger isGreaterThan(int i2) {
        if (i <= i2) {
            throw new AssertionError(String.format("%d <= %d", i, i2));
        }
        return this;
    }
}
