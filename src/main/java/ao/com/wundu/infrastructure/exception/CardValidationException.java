package ao.com.wundu.infrastructure.exception;

public class CardValidationException extends RuntimeException {
    public CardValidationException(String message) {
        super(message);
    }
}
