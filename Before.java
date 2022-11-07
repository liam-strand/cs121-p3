/* Before.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * An annotation for functions that should be run before each test case.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}
