package  exceptions;

public class BaseDeDonneesException extends LivreException {
    public BaseDeDonneesException(String message) {
        super(message);
    }
    
    public BaseDeDonneesException(String message, Throwable cause) {
        super(message, cause);
    }
}
