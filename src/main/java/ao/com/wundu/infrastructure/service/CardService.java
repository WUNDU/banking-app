package ao.com.wundu.infrastructure.service;

import ao.com.wundu.api.dto.CardRequest;
import ao.com.wundu.api.dto.CardResponse;
import ao.com.wundu.api.dto.CardValidateRequest;
import ao.com.wundu.api.dto.CardValidateResponse;
import ao.com.wundu.domain.model.BankAccount;

public interface CardService {

    CardResponse create(CardRequest request);
    CardResponse findById(Long cardId);

    CardValidateResponse validateCard(CardValidateRequest request);

    CardResponse getDecryptedAccountNumber(Long id);
}
