package ao.com.wundu.infrastructure.service.impl;

import ao.com.wundu.api.dto.CardRequest;
import ao.com.wundu.api.dto.CardResponse;
import ao.com.wundu.api.mapper.CardMapper;
import ao.com.wundu.domain.model.Card;
import ao.com.wundu.infrastructure.exception.ResourceNotFoundException;
import ao.com.wundu.infrastructure.repository.BankAccountRepository;
import ao.com.wundu.infrastructure.repository.CardRepository;
import ao.com.wundu.infrastructure.service.CardService;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.util.AccountNumberGenerator;
import ao.com.wundu.util.AesEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override
    public CardResponse create(CardRequest request) {

        // Validar expirationDate (mínimo 6 meses no futuro)
        if (request.expirationDate().isBefore(LocalDate.now().plusMonths(6))) {
            throw new IllegalArgumentException("Expiration date must be at least 6 months in the future");
        }


        BankAccount bankAccount = bankAccountRepository.findById(request.accountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));


        if (bankAccount.getCards().size() >= 3) {
            throw new IllegalStateException("Limite de cartões atingido");
        }

        Card entity = CardMapper.toEntity(request, bankAccount);
        String rawAccountNumber = AccountNumberGenerator.generate();

        String encryptedAccountNumber = AesEncryptionUtil.encrypt(rawAccountNumber);

        entity.setCardNumber(encryptedAccountNumber);

        Card savedCard = cardRepository.save(entity);

        return CardMapper.toResponse(savedCard);
    }

    @Override
    public CardResponse findById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow( () -> new ResourceNotFoundException("Cartão não encontrado") );

        return CardMapper.toResponse(card);
    }
}
