package ao.com.wundu.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CardRequest(

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

//        @NotBlank(message = "Last four digits cannot be empty")
//        @Size(min = 4, max = 4, message = "Last four digits must be exactly 4 digits")
//        @Pattern(regexp = "\\d{4}", message = "Last four digits must contain only digits")
//        String lastFourDigits,

//        @NotBlank(message = "Bank name cannot be empty")
//        @Size(min = 1, max = 100, message = "Bank name must be between 1 and 100 characters")
//        String bankName,

        @NotNull(message = "Expiration date cannot be null")
        @Future(message = "Expiration date must be in the future, at least 6 months from now")
        LocalDate expirationDate
) {
}
