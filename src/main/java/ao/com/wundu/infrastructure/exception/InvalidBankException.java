package ao.com.wundu.infrastructure.exception;

public class InvalidBankException extends RuntimeException {
    public InvalidBankException(String message) {
        super(message);
    }
}
