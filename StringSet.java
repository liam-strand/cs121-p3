/* StringSet.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A type annotation that indicates we want to test the strings in the set.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE_USE })
public @interface StringSet {
  public String[] strings() default "";
}
