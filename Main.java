import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Throwable> tests = Unit.testClass("SomeClass");

        for (Map.Entry<String, Throwable> test : tests.entrySet()) {
            String success = test.getValue() == null ? "PASS" : String.format("FAIL -> %s", test.getValue().getMessage());
            System.out.println(String.format("%s: %s", test.getKey(), success));
        }
    }
}
