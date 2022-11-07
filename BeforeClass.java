/* BeforeClass.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * An annotation for functions that should be run before all test cases.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeClass {
}
