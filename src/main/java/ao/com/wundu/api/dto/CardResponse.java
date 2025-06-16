package ao.com.wundu.api.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public record CardResponse(
        Long id,
        String bankName,
        String cardNumber,
        String expirationDate,
        Long accountId,
        Timestamp createdAt
) {
}
