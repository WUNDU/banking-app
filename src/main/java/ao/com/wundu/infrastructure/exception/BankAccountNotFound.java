package ao.com.wundu.infrastructure.exception;

public class BankAccountNotFound extends RuntimeException {

    public BankAccountNotFound(String msg) {
        super(msg);
    }
}
