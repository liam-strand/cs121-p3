# Testing in Java

<details>

<summary>Ethos</summary>

As software engineers, it is critical that we verify the accuracy of our software. Most languages have unit-testing libraries to facilitate this:

- C++ has `googletest`
- Rust has `cargo`
- Python has `pytest`
- etc...

Copious and thorough unit testing, when used in consort with static analysis tools and wise integration testing strategies, can give a software engineer confidence that they have writen a module correctly.

Though plenty of unit testing frameworks exist already, the topic is important enough to warent close examination. Furthermore, it provides an opportunity to explore Java's reflection API and develop our own fluent API.

Finally, we write a version of QuickCheck, the Haskell property testing library, because fuzzy testing by hand can become tiresome!

</details>

This project contains 4 components:

1. Unit Testing Framework
2. Property Testing Framework
3. Fluent Assertion API
4. Test Runner


## Unit Testing Framework

The key endpoint of the unit testing framework is the method:

```java
public static Map<String, Throwable> testClass(String name);
```

Given a class name, this method runs all test cases in that class. The return value is a mapping of test case names to the exception or error that is thrown by the test case. If the test case name maps to `null`, then nothing was thrown and the test case succeeded.

Test cases are annotated with `@Test`, and the following setup and teardown methods are defined:

- `@Before` runs before each test case
- `@After` runs after each test case
- `@BeforeClass` runs once before all test cases are run, not between test cases
- `@AfterClass` runs once after all test cases have been run

## Property Testing Framework

The key endpoint of the property testing framework is the method:

```java
public static Map<String, Object[]> quickCheckClass(String name);
```

Given a class name, this method runs all property tests in that class. The return value is a map of property test names to an array of arguments on which the property test failed.

For example,
```java
@Property
public boolean absNonNeg(@IntRange(min=-10, max=10) Integer i) {
    return Math.abs(i.intValue()) >= 0;
}
```

represents a property test where we want to verify that every `Integer` between -10 and 10 has an absolute value that is non-negative. The `@IntRange` annotation indicates that we want to test every integer between -10 and 10.

Other annotations are available for `String`s, `List`s, and generic `Object`s.

A property test "fails" when it returns `false` or throws any exception or error.
## Fluent Assertion API

Unit and property tests require users to write a **lot** of assertions. It is important to make that process as seamless as possible. A "fluent" API is one way to do that. Here's an example of a fluent assertion:

```java
String s = "Hello, testing!";
Assertion.assertThat(s).isNotNull().startsWith("Liam");
```

The advantage of a fluent API is that the user can chain assertions in an intuitive, readable way. In this example, one can see what is being tested simply by reading across the test!
## Test Runner

This component is a way to package the `testClass` and `quickCheckClass` APIs into something a user could actually run. Simply invoke the JVM on the `TestRunner` and provide the name of the class to test as the first command-line-argument.

The test runner runs both unit and quickCheck tests, organizes their results, and prints it all out in a user-friendly color-coded format.