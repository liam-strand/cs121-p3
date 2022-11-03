public class AssertingString {
    private String s;
    public AssertingString(String s) {
        this.s = s;
    }
    public AssertingString isNotNull() {
        if (s == null) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString isNull() {
        if (s != null) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString isEqualTo(Object o2) {
        if (!s.equals(o2)) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString isNotEqualTo(Object o2) {
        if (s.equals(o2)) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString startsWith(String s2) {
        if (!s.startsWith(s2)) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString isEmpty() {
        if (!s.isEmpty()) {
            throw new AssertionException();
        }
        return this;
    }
    public AssertingString contains(String s2) {
        if (!s.contains(s2)) {
            throw new AssertionException();
        }
        return this;
    }
}
