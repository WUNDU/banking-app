package ao.com.wundu.infrastructure.exception;

public class BankingApiException extends RuntimeException {

    public BankingApiException (String msg) {
        super(msg);
    }
}
