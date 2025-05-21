package ao.com.wundu.api.dto;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long accountId,
        Long cardId,
        Double amount,
        String merchant,
        String type,
        LocalDateTime dateTime,
        String description
) {
}
