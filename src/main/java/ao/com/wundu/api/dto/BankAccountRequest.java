package ao.com.wundu.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BankAccountRequest(
        @NotBlank(message = "Bank name cannot be empty")
        @Size(min = 1, max = 100, message = "Bank name must be between 1 and 100 characters")
        String bankName
) {
}
