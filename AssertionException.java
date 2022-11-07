/* AssertionException.java
 * 
 * By: Liam Strand
 * On: November 2022
 * 
 * A runtime exception that is thrown by the fluent assertion system.
 */

public class AssertionException extends RuntimeException {
    public AssertionException() {
        super();
    }
    public AssertionException(String message) {
        super(message);
    }
    public AssertionException(Throwable cause) {
        super(cause);
    }
    public AssertionException(String message, Throwable cause) {
        super(message, cause);
    }
}
