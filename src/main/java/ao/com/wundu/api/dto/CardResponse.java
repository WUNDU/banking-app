package ao.com.wundu.api.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public record CardResponse(
        Long id,
        String lastFourDigits,
        String bankName,
        LocalDate expirationDate,
        Long accountId,
        Timestamp createdAt
) {
}
