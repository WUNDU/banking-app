package ao.com.wundu.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ExpirationDateUtil {

    private static final DateTimeFormatter DD_MM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM");
    private static final DateTimeFormatter MM_YY_FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    /**
     * Valida se a data de expiração é pelo menos 6 meses do futuro
     * e ajusta para o último dia do mês
     */
    public static LocalDate validateAndAdjustExpirationDate(LocalDate expirationDate) {

        if (expirationDate == null) {
            throw new IllegalArgumentException("Data de expiração não pode ser nula");
        }

        // Validar se a data original é válida (exemplo: 31/02 deve lançar exceção)
        try {
            // Tenta criar a data para verificar se é válida
            LocalDate.of(expirationDate.getYear(), expirationDate.getMonth(), expirationDate.getDayOfMonth());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("Data inválida: %02d/%02d/%d não existe no calendário",
                            expirationDate.getDayOfMonth(),
                            expirationDate.getMonthValue(),
                            expirationDate.getYear())
            );
        }

        // Ajustar para o último dia do mês
        YearMonth yearMonth = YearMonth.from(expirationDate);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        // Validar se está pelo menos 6 meses no futuro
        LocalDate minimumDate = LocalDate.now().plusMonths(6);
        if (lastDayOfMonth.isBefore(minimumDate)) {
            throw new IllegalArgumentException(
                    String.format("Data de expiração deve ser pelo menos 6 meses no futuro. " +
                                    "Mínimo: %s, Fornecido: %s",
                            formatToDDMM(minimumDate),
                            formatToDDMM(lastDayOfMonth))
            );
        }

        return lastDayOfMonth;
    }

    /**
     * Formata a data para dd/MM (dia/mês)
     */
    public static String formatToDDMM(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DD_MM_FORMATTER);
    }

    /**
     * Formata a data para MM/yy (mês/ano) - mantido para compatibilidade
     */
    public static String formatToMMYY(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(MM_YY_FORMATTER);
    }

    /**
     * Converte dd/MM String para LocalDate (último dia do mês do ano atual)
     */
    public static LocalDate parseFromDDMM(String ddmm) {
        if (ddmm == null || ddmm.trim().isEmpty()) {
            throw new IllegalArgumentException("Formato dd/MM não pode ser nulo ou vazio");
        }

        try {
            // Adiciona o ano atual para fazer o parse
            int currentYear = LocalDate.now().getYear();
            String fullDate = ddmm + "/" + currentYear;
            LocalDate parsedDate = LocalDate.parse(fullDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Retorna o último dia do mês
            YearMonth yearMonth = YearMonth.from(parsedDate);
            return yearMonth.atEndOfMonth();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato inválido. Use dd/MM (ex: 31/12)");
        }
    }

    /**
     * Converte MM/yy String para LocalDate (último dia do mês) - mantido para compatibilidade
     */
    public static LocalDate parseFromMMYY(String mmyy) {
        if (mmyy == null || mmyy.trim().isEmpty()) {
            throw new IllegalArgumentException("Formato MM/yy não pode ser nulo ou vazio");
        }

        try {
            YearMonth yearMonth = YearMonth.parse(mmyy, MM_YY_FORMATTER);
            return yearMonth.atEndOfMonth();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato inválido. Use MM/yy (ex: 02/28)");
        }
    }

    /**
     * Valida se uma data específica existe no calendário
     */
    public static boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
