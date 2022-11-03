import java.util.*;

public class SomeClass {
    static final boolean DEBUG = false;

    @Test
    public static void test1() {
        if (DEBUG) { 
            System.err.println("test1");
        }
    }

    @Test
    public static void another_test() {
        if (DEBUG) { 
            System.err.println("another_test");
        }
        throw new RuntimeException("hey this was an intentional failure");
    }

    @Test
    public static void assertingObjectAffirmativeTest() {
        Character c = 'l';

        Assertion.assertThat((Object)c).isEqualTo('l').isNotNull().isInstanceOf(Character.class);

        Assertion.assertThat((Object)null).isNull();
    }

    @Test
    public static void assertingObjectNegativeTest() {
        Character c = 'z';

        Assertion.assertThat((Object)c).isEqualTo('a');
    }

    @Test
    public static void assertingStringAffirmativeTest() {
        String s = "hello";
        Assertion.assertThat(s).isNotNull().isEqualTo("hello").isNotEqualTo("hello!").contains("lo").startsWith("hell");
        Assertion.assertThat(null).isNull();
    }

    @Test
    public static void assertingStringNegativeTest() {
        String s = "hello";
        Assertion.assertThat(s).isNotNull().contains("ol");
    }

    @Test
    public static void assertingBooleanAffirmativeTest() {
        boolean b = true;
        Assertion.assertThat(b).isEqualTo(true);
        Assertion.assertThat(false).isEqualTo(false);
        Assertion.assertThat(b).isTrue();
    }

    @Test
    public static void assertingIntegerAffirmativeTest() {
        int i = 5;
        Assertion.assertThat(i).isEqualTo(5).isGreaterThan(0).isLessThan(10).isGreaterThan(-100);
    }

    @Test
    public static void assertingIntegerNegativeTest() {
        int i = 5;
        Assertion.assertThat(i).isEqualTo(5).isGreaterThan(0).isLessThan(10).isGreaterThan(100);
    }

    @Before
    public static void print_something() {
        if (DEBUG) { 
            System.err.println("before");
        }
    }

    @After
    public static void print_something_else() {
        if (DEBUG) { 
            System.err.println("after");
        }
    }

    @BeforeClass
    public static void print_a_thing() {
        if (DEBUG) { 
            System.err.println("before class");
        }
    }

    @AfterClass
    public static void print_a_kicker() {
        if (DEBUG) { 
            System.err.println("after class");
        }
    }


    @Property
    public boolean absNonNeg(@IntRange(min=-10, max=10) Integer i) {
        return Math.abs(i.intValue()) >= 0; 
    } 

    @Property
    public boolean isS2(@StringSet(strings={"s1", "s2", "s3", "a1"}) String s) {
        if(DEBUG) {
            System.out.println(s);
        }
        return s.startsWith("s");
    }

    @Property
    public void listOfInts(@ListLength(min=0, max=2) List<@IntRange(min=5, max=7) Integer> l) {
        if(DEBUG) {
            System.out.println(l.toString());
        }
    }

    @Property
    public void listOfLists(@ListLength(min=0, max=2) List<@ListLength(min=0, max=2) List<@IntRange(min=5, max=7) Integer>> l) {
        if(DEBUG) {
            System.out.println(l.toString());
        }
    }

    @Property
    public void deepLists(@ListLength(min=0, max=2) List<@ListLength(min=0, max=2) List<@ListLength(min=0, max=2) List<@ListLength(min=0, max=2) List<@IntRange(min=5, max=5) Integer>>>> l) {
        if(DEBUG) {
            System.out.println(l.toString());
        }
    }

    @Property
    public void objectGen(@ForAll(name="genSeven", times=10) Object i) {
        if(DEBUG) {
            System.out.println(i.toString());
        }

        if (i instanceof Integer) {
            Integer i_nt = (Integer)i;
            i_nt += 1;
        } else {
            throw new RuntimeException();
        }

    }

    public Object genSeven() {
        return 7;
    }
}
