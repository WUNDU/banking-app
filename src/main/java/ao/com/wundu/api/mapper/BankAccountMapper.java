package ao.com.wundu.api.mapper;

import ao.com.wundu.api.dto.BankAccountRequest;
import ao.com.wundu.api.dto.BankAccountResponse;
import ao.com.wundu.domain.model.BankAccount;

import java.util.List;
import java.util.stream.Collectors;

public class BankAccountMapper {

    public static BankAccount toEntity(BankAccountRequest request) {
        BankAccount entity = new BankAccount();

        entity.setBankName(request.bankName());

        return entity;
    }

    public static BankAccountResponse toResponse(BankAccount account) {

        return new BankAccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBankName(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }

    public static List<BankAccountResponse> toResponseList(List<BankAccount> accounts) {
        return accounts.stream()
                .map(BankAccountMapper::toResponse)
                .collect(Collectors.toList());
    }

}
