public class Assertion {

    /* You'll need to change the return type of the assertThat methods */
    static AssertingObject assertThat(Object o) {
        return new AssertingObject(o);
    }

    static AssertingString assertThat(String s) {
        return new AssertingString(s);
    }

    static AssertingBoolean assertThat(boolean b) {
        return new AssertingBoolean(b);
    }

    static AssertingInteger assertThat(int i) {
        return new AssertingInteger(i);
    }
}
