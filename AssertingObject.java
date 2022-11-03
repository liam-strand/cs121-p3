public class AssertingObject {
    private Object o;
    public AssertingObject(Object o) {
        this.o = o;
    }

    public AssertingObject isNotNull() {
        if (o == null) {
            throw new AssertionException("o should not be null, but it is null");
        }
        return this;
    }
    public AssertingObject isNull() {
        if (o != null) {
            throw new AssertionException(String.format("o should be null, but it is %s", o.toString()));
        }
        return this;
    }
    public AssertingObject isEqualTo(Object o2) {
        if (!o.equals(o2)) {
            throw new AssertionException(String.format("o != o2 because o = %s and o2 = %s", o.toString(), o2.toString()));
        }
        return this;
    }
    public AssertingObject isNotEqualTo(Object o2) {
        if (o.equals(o2)) {
            throw new AssertionException(String.format("o == o2 because o == o2 == %s", o.toString()));
        }
        return this;
    }
    public AssertingObject isInstanceOf(Class<?> c) {
        if (!c.isInstance(o)) {
            throw new AssertionException(String.format("o is not an instance of c because o is of type %s and c is %s", o.getClass().toString(), c.toString()));
        }
        return this;
    }
}
