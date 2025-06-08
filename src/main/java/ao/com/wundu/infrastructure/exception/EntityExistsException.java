package ao.com.wundu.infrastructure.exception;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String msg) {
        super(msg);
    }
}
