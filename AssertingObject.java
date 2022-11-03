public class AssertingObject {
    private Object o;
    public AssertingObject(Object o) {
        this.o = o;
    }

    public AssertingObject isNotNull() {
        if (o == null) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingObject isNull() {
        if (o != null) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingObject isEqualTo(Object o2) {
        if (!o.equals(o2)) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingObject isNotEqualTo(Object o2) {
        if (o.equals(o2)) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(o)) {
            throw new AssertionException();
        }
        return this;
    }
}
