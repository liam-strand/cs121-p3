public class AssertingObject {
    private Object o;
    public AssertingObject(Object o) {
        this.o = o;
    }

    public AssertingObject isNotNull() {
        if (o == null) {
            throw new AssertionError(String.format("%s is null", o.toString()));
        }
        return this;
    }
    public AssertingObject isNull() {
        if (o != null) {
            throw new AssertionError(String.format("%s is not null", o.toString()));
        }
        return this;
    }
    public AssertingObject isEqualTo(Object o2) {
        if (o == null && o2 != null) {
            throw new AssertionError(String.format("%s is null but %s is not, so they are not equal", o.toString(), o2.toString()));
        } else if (o != null && !o.equals(o2)) {
            throw new AssertionError(String.format("%s is not equal to %s", o.toString(), o2.toString()));
        }
        return this;
    }
    public AssertingObject isNotEqualTo(Object o2) {
        if (o == null && o2 == null) {
            throw new AssertionError(String.format("%s and %s are null, so they are equal", o.toString(), o2.toString()));
        } else if (o != null && o.equals(o2)) {
            throw new AssertionError(String.format("%s is equal to %s", o.toString(), o2.toString()));
        }
        return this;
    }
    public AssertingObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(o)) {
            throw new AssertionError(String.format("%s is not an instance of %s", o.toString(), c.toString()));
        }
        return this;
    }
}
