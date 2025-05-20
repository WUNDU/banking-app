package ao.com.wundu.api.dto;

import java.sql.Timestamp;

public record BankAccountResponse(

        Long id,
        String accountNumber,
        String bankName,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}
