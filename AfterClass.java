/* AfterClass.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * An annotation for functions that should be run after all tests, not before
 * each test.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterClass {
}
