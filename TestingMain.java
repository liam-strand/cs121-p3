import java.util.*;

public class TestingMain {
    public static void main(String[] args) {
        Map<String, Throwable> tests = Unit.testClass("SomeClass");
        printTests(tests);

        Map<String, Object[]> props = Unit.quickCheckClass("SomeClass");
        printProps(props);
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
        Collections.sort(testnames);
        String formatter = String.format("%%%ds %%s%s", longest_test, ConsoleColors.RESET);
        for (String s : testnames) {
            Throwable ex = tests.get(s);
            boolean success = ex == null;
            String report = (success ? String.format("%sPASS", ConsoleColors.GREEN) 
                                     : String.format("%sFAIL -> %s", ConsoleColors.RED, ex.getMessage()));
            String raw_line = String.format(formatter, s, report);
            System.out.println(raw_line);
        }
    }

    private static void printProps(Map<String, Object[]> props) {
        List<String> testnames = new ArrayList<>(props.size());
        for (String test : props.keySet()) {
            testnames.add(test);
        }
        int longest_test = 0;
        for (String s : testnames) {
            if (s.length() > longest_test) {
                longest_test = s.length();
            }
        }
        Collections.sort(testnames);
        String formatter = String.format("%%%ds %%s%s", longest_test, ConsoleColors.RESET);
        for (String s : testnames) {
            Object[] ex = props.get(s);
            boolean success = ex == null;
            String report = (success ? String.format("%sPASS", ConsoleColors.GREEN) 
                                     : String.format("%sFAIL -> %s", ConsoleColors.RED, ex.toString()));
            String raw_line = String.format(formatter, s, report);
            System.out.println(raw_line);
        }
    }
}
