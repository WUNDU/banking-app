package ao.com.wundu.infrastructure.service;

import ao.com.wundu.api.dto.BankAccountRequest;
import ao.com.wundu.api.dto.BankAccountResponse;

import java.util.Optional;

public interface BankAccountService {

    BankAccountResponse findById(Long id);
    BankAccountResponse create(BankAccountRequest request);
}
