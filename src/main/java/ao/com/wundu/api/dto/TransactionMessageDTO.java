package ao.com.wundu.api.dto;

import java.time.LocalDateTime;

public record TransactionMessageDTO(

        Long cardId,
        Double amount,
        String description,
        String type,
        LocalDateTime dateTime
) {
}
