package ao.com.wundu.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ExpirationDateUtil {

    private static final DateTimeFormatter MM_YY_FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    /**
     * VAlida se a data de expiração é pelo menos 6 meses do futuro
     * e ajusta para o último dia do mês
     */
    public static LocalDate validateAndAdjustExpirationDate(LocalDate expirationDate) {

        if (expirationDate == null) {
            throw new IllegalArgumentException("Data de expiração não pode ser nula");
        }

        YearMonth yearMonth = YearMonth.from(expirationDate);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        LocalDate minimumDate = LocalDate.now().plusMonths(6);
        if ( lastDayOfMonth.isBefore(minimumDate) ) {
            throw new IllegalArgumentException(
                    String.format("Data de expiração deve ser pelo menos 6 meses no futuro. " +
                                    "Mínimo: %s, Fornecido: %s",
                            formatToMMYY(minimumDate),
                            formatToMMYY(lastDayOfMonth))
            );
        }

        return lastDayOfMonth;
    }

    /**
     * Forma a data para MM/yy
     */
    public static String formatToMMYY(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(MM_YY_FORMATTER);
    }

    /**
     * Converte MM/yy String para LocalDate (ùltimo dia do Mês)
     */
    public static LocalDate parseFromMMYY(String mmyy) {
        if (mmyy == null || mmyy.trim().isEmpty() ) {
            throw new IllegalArgumentException("Formato MM/yy não pode ser nulo ou vazio");
        }

        try {
            YearMonth yearMonth = YearMonth.parse(mmyy, MM_YY_FORMATTER);
            return yearMonth.atEndOfMonth();
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato inválido. Use MM/yy (ex: 02/28)");
        }
    }

}
