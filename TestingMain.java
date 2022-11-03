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
            Object[] args = props.get(s);
            boolean success = args == null;
            String report;
            if (success) {
                report = String.format("%sPASS", ConsoleColors.GREEN);
            } else {
                String args_str = "[";
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (i != 0) {
                        args_str += ", ";
                    }
                    if (arg instanceof String) {
                        args_str = args_str + "\"" + arg.toString() + "\"";
                    } else {
                        args_str += arg.toString();
                    }
                }
                args_str += "]";
                report = String.format("%sFAIL -> %s", ConsoleColors.RED, args_str);
            }

            String raw_line = String.format(formatter, s, report);
            System.out.println(raw_line);
        }
    }
}
