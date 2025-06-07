package ao.com.wundu.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionEventDTO(
        Long externalCardId,
        Double amount,
        String description,
        String type,
        LocalDateTime dateTime
) {

}
