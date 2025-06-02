package ao.com.wundu.api.dto;

import java.io.Serializable;
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
) implements Serializable {

    private static final long serialVersionUID = 1L;
}
