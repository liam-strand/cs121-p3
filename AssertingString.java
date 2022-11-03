public class AssertingString {
    private String s;
    public AssertingString(String s) {
        this.s = s;
    }
    public AssertingString isNotNull() {
        if (s == null) {
            throw new AssertionError(String.format("s is null"));
        }
        return this;
    }
    public AssertingString isNull() {
        if (s != null) {
            throw new AssertionError(String.format("s is not null"));
        }
        return this;
    }
    public AssertingString isEqualTo(Object o2) {
        if (!s.equals(o2)) {
            throw new AssertionError(String.format("%s is not equal to %s", s, o2.toString()));
        }
        return this;
    }
    public AssertingString isNotEqualTo(Object o2) {
        if (s.equals(o2)) {
            throw new AssertionError(String.format("%s is equal to %s", s, o2.toString()));
        }
        return this;
    }
    public AssertingString startsWith(String s2) {
        if (!s.startsWith(s2)) {
            throw new AssertionError(String.format("%s does not start with %s", s, s2));
        }
        return this;
    }
    public AssertingString isEmpty() {
        if (!s.isEmpty()) {
            throw new AssertionError("s is not empty");
        }
        return this;
    }
    public AssertingString contains(String s2) {
        if (!s.contains(s2)) {
            throw new AssertionError(String.format("%s does not contain %s", s, s2));
        }
        return this;
    }
}
