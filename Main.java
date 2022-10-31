import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Throwable> tests = Unit.testClass("SomeClass");
        printTests(tests);
    }
    
    private static void printTests(Map<String, Throwable> tests) {
        List<String> testnames = new ArrayList<>(tests.size());
        for (String test : tests.keySet()) {
            testnames.add(test);
        }

        int longest_test = 0;
        for (String s : testnames) {
            if (s.length() > longest_test) {
                longest_test = s.length();
            }
        }

        String formatter = String.format("%%%ds %%s%%n", longest_test);
        for (String s : testnames) {
            Throwable ex = tests.get(s);
            System.out.printf(formatter, s, ex == null ? "PASS" : String.format("FAIL -> %s", ex.getMessage()));
        }
    }
}
