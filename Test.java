/* Test.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A method annotation that indicates that this function is a test method.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
