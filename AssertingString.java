/* AssertingString.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A wrapper around a String that enables fluent assertions.
 */

public class AssertingString {
    private String s;
    public AssertingString(String s) {
        this.s = s;
    }
    public AssertingString isNotNull() {
        if (s == null) {
            throw new AssertionException("s should not be null, but it is null");
        }
        return this;
    }
    public AssertingString isNull() {
        if (s != null) {
            throw new AssertionException(String.format("s should be null, instead it is \"%s\"", s));
        }
        return this;
    }
    public AssertingString isEqualTo(Object o2) {
        if (!s.equals(o2)) {
            throw new AssertionException(String.format("s != s2 because s = \"%s\" and s2 = \"%s\"", s, o2.toString()));
        }
        return this;
    }
    public AssertingString isNotEqualTo(Object o2) {
        if (s.equals(o2)) {
            throw new AssertionException(String.format("s == s2 because s == s2 = \"%s\"", s));
        }
        return this;
    }
    public AssertingString startsWith(String s2) {
        if (!s.startsWith(s2)) {
            throw new AssertionException(String.format("s does not start with s2 because s = \"%s\" and s2 = \"%s\"", s, s2));
        }
        return this;
    }
    public AssertingString isEmpty() {
        if (!s.isEmpty()) {
            throw new AssertionException(String.format("s is not empty, instead it is \"%s\"", s));
        }
        return this;
    }
    public AssertingString contains(String s2) {
        if (!s.contains(s2)) {
            throw new AssertionException(String.format("s does not contain s2 because s = \"%s\" and s2 = \"%s\"", s, s2));
        }
        return this;
    }
}
