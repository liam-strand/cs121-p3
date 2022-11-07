/* Assertion.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A class that generates asserting objects for fluent assertions.
 */

public class Assertion {

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
