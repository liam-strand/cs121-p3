/* Property.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A method annotation that we want to treat a method as a Property test.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Property {
}
