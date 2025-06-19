package ao.com.wundu.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CardValidateRequest(
        @NotBlank(message = "cardNumber é obrigatório")
        @Size(min = 16, max = 16, message = "cardNumber deve ter 16 dígitos")
        String cardNumber,
//        @NotBlank(message = "bankName é obrigatório") -> Agora
        String bankName,
        @NotNull(message = "expirationDate é obrigatório")
        @Future(message = "expirationDate deve ser futura")
        LocalDate expirationDate
) {
}
