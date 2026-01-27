package  exceptions;

public class LivreException extends Exception {
    public LivreException(String message) {
        super(message);
    }
    
    public LivreException(String message, Throwable cause) {
        super(message, cause);
    }
}
