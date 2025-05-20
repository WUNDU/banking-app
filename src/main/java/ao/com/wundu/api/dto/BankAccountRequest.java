package ao.com.wundu.api.dto;

public record BankAccountRequest(

        String accountNumber,
        String bankName
) {
}
