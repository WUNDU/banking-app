package ao.com.wundu.infrastructure.exception;

public class CardLimitExceededException extends RuntimeException {
    public CardLimitExceededException(String message) {
        super(message);
    }
}
