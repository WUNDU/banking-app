package ao.com.wundu.api.dto;

import java.time.ZonedDateTime;

public record ErrorResponseDTO(
        ZonedDateTime timestamp,
        int status,
        String error,
        String message,
        String code,
        String path
) {
    public ErrorResponseDTO(int status, String error, String message, String code, String path) {
        this(ZonedDateTime.now(), status, error, message, code, path);
    }
}
