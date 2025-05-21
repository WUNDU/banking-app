package ao.com.wundu.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TransactionRequest(

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

        Long cardId,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be greater than zero")
        Double amount,

        @NotBlank(message = "Merchant cannot be empty")
        @Size(min = 1, max = 100, message = "Merchant must be between 1 and 100 characters")
        String merchant,

        @NotBlank(message = "Type cannot be empty")
        @Size(min = 1, max = 50, message = "Type must be between 1 and 50 characters")
        String type,

        @NotNull(message = "Date and time cannot be null")
        LocalDateTime dateTime,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description
) {


}
