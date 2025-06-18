package ao.com.wundu.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CardRequest(

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

        @NotNull(message = "Expiration date cannot be null")
        @Future(message = "Expiration date must be in the future, at least 6 months from now")
//        @JsonFormat(pattern = "yyyy/MM/dd", shape = JsonFormat.Shape.STRING)
        LocalDate expirationDate
) {

//
//        /**
//         * Método helper para obter a data de expiração como LocalDate
//         */
//        public LocalDate getExpirationDateAsLocalDate() {
//                if (expirationDate instanceof LocalDate) {
//                        return (LocalDate) expirationDate;
//                } else if (expirationDate instanceof String) {
//                        return LocalDate.parse((String) expirationDate,
//                                java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//                } else {
//                        throw new IllegalArgumentException("Formato de data inválido");
//                }
//        }
//
//        /**
//         * Método helper para obter a data de expiração como String
//         */
//        public String getExpirationDateAsString() {
//                if (expirationDate instanceof String) {
//                        return (String) expirationDate;
//                } else if (expirationDate instanceof LocalDate) {
//                        return ((LocalDate) expirationDate).format(
//                                java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//                } else {
//                        throw new IllegalArgumentException("Formato de data inválido");
//                }
//        }
}
