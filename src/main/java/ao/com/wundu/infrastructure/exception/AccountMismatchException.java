package ao.com.wundu.infrastructure.exception;

public class AccountMismatchException extends RuntimeException{
    public AccountMismatchException(String message) {
        super(message);
    }
}
