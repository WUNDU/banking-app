package ao.com.wundu.infrastructure.service.impl;

import ao.com.wundu.api.dto.BankAccountRequest;
import ao.com.wundu.api.dto.BankAccountResponse;
import ao.com.wundu.api.mapper.BankAccountMapper;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.infrastructure.exception.ResourceNotFoundException;
import ao.com.wundu.infrastructure.repository.BankAccountRepository;
import ao.com.wundu.infrastructure.service.BankAccountService;
import ao.com.wundu.util.AccountNumberGenerator;
import ao.com.wundu.util.AesEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository accountRepository;


    @Override
    public BankAccountResponse findById(Long id) {

        BankAccount account = accountRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Bank account não encontado") );

        return BankAccountMapper.toResponse(account);

    }

    @Override
    public BankAccountResponse create(BankAccountRequest request) {

        BankAccount entity = BankAccountMapper.toEntity(request);

        String rawAccountNumber = AccountNumberGenerator.generate();

        String encryptedAccountNumber = AesEncryptionUtil.encrypt(rawAccountNumber);

        entity.setAccountNumber(encryptedAccountNumber);

        BankAccount saved = accountRepository.save(entity);

        return BankAccountMapper.toResponse(saved);
    }

    @Override
    public BankAccountResponse getDecryptedAccountNumber(Long id) {

        BankAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account não encontado"));

        String decryptedAccountNumber = AesEncryptionUtil.decrypt(account.getAccountNumber());

        BankAccountResponse accountResponse = BankAccountMapper.toResponse(account);

        return new BankAccountResponse(
                accountResponse.id(),
                decryptedAccountNumber,
                accountResponse.bankName(),
                accountResponse.createdAt(),
                accountResponse.updatedAt()
        );
    }
}
