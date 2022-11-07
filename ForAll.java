/* ForAll.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A type annotation that indicates that we want to run a function to generate
 * an Object. 
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE_USE })
public @interface ForAll {
  public String name() default "";

  public int times() default 3;
}
