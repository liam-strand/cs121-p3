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
        Character s = 'l';

        Assertion.assertThat((Object)s).isEqualTo('l').isNotNull().isInstanceOf(Character.class);

        Assertion.assertThat((Object)null).isNotEqualTo("jeff").isNull();
    }

    @Test
    public static void assertingObjectNegativeTest() {
        Character c = 'z';

        Assertion.assertThat((Object)c).isEqualTo('a');
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
}
