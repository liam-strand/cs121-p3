/* IntRange.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A type annotation that indicates we want to test a range of integers.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE_USE })
public @interface IntRange {
  public int min() default 0;

  public int max() default 5;
}
