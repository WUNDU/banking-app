package ao.com.wundu.infrastructure.service.impl;

import ao.com.wundu.api.dto.*;
import ao.com.wundu.api.mapper.BankAccountMapper;
import ao.com.wundu.api.mapper.CardMapper;
import ao.com.wundu.domain.model.Card;
import ao.com.wundu.infrastructure.exception.*;
import ao.com.wundu.infrastructure.repository.BankAccountRepository;
import ao.com.wundu.infrastructure.repository.CardRepository;
import ao.com.wundu.infrastructure.service.CardService;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.util.AccountNumberGenerator;
import ao.com.wundu.util.AesEncryptionUtil;
import ao.com.wundu.util.ExpirationDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override
    public CardResponse create(CardRequest request) {

        try {
            logger.info("Criando cartão para accountId={}, expirationDate={}",
                    request.accountId(), request.expirationDate());
            BankAccount bankAccount = bankAccountRepository.findById(request.accountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

//            if (bankAccount.getCards().size() >= 3) {
//                throw new CardLimitExceededException("Limite máximo de 3 cartões por conta atingido");
//            }

            Card entity = CardMapper.toEntity(request, bankAccount);
            String rawCardNumber = AccountNumberGenerator.generate();
            String encryptedCardNumber = AesEncryptionUtil.encrypt(rawCardNumber);

            entity.setCardNumber(encryptedCardNumber);
            Card savedCard = cardRepository.save(entity);

            logger.info("Cartão criado com sucesso: cardId={}, expirationDate={}",
                    savedCard.getId(), savedCard.getExpirationDate());

            return CardMapper.toResponse(savedCard);

        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação na criação do cartão: {}", e.getMessage());
            throw new InvalidExpirationDAteException(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado na criação do cartão: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno na criação do cartão");
        }

//        // Validar expirationDate (mínimo 6 meses no futuro)
//        if (request.expirationDate().isBefore(LocalDate.now().plusMonths(6))) {
//            throw new IllegalArgumentException("Expiration date must be at least 6 months in the future");
//        }
//
//
//        BankAccount bankAccount = bankAccountRepository.findById(request.accountId())
//                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
//
//
////        if (bankAccount.getCards().size() >= 3) {
////            throw new IllegalStateException("Limite de cartões atingido");
////        }
//
//        Card entity = CardMapper.toEntity(request, bankAccount);
//        String rawAccountNumber = AccountNumberGenerator.generate();
//
//        String encryptedAccountNumber = AesEncryptionUtil.encrypt(rawAccountNumber);
//
//        entity.setCardNumber(encryptedAccountNumber);
//
//        Card savedCard = cardRepository.save(entity);
//
//        return CardMapper.toResponse(savedCard);
    }

    @Override
    public CardResponse findById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow( () -> new ResourceNotFoundException("Cartão não encontrado") );

        return CardMapper.toResponse(card);
    }

    @Override
    public CardResponse getDecryptedAccountNumber(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card account não encontado"));

        String decryptedAccountNumber = AesEncryptionUtil.decrypt(card.getCardNumber());

        CardResponse cardResponse = CardMapper.toResponse(card);

        return new CardResponse(
                cardResponse.id(),
                cardResponse.bankName(),
                decryptedAccountNumber,
                cardResponse.expirationDate(),
                cardResponse.accountId(),
                cardResponse.createdAt()
        );
    }

    @Override
    public CardValidateResponse validateCard(CardValidateRequest request) {

        logger.info("Validando cartão: bankName={}", request.bankName());

        try {
            // Descriptografar número do cartão para comparação
            String encryptedCardNumber = AesEncryptionUtil.encrypt(request.cardNumber());
            Card card = cardRepository.findByCardNumber(encryptedCardNumber)
                    .orElseThrow(() -> {
                        logger.error("Cartão não encontrado: cardNumber=****{}",
                                request.cardNumber().substring(12));
                        return new ResourceNotFoundException("Cartão não encontrado");
                    });

            // Validar bankName
            if (!card.getBankAccount().getBankName().equals(request.bankName())) {
                logger.error("Banco inválido: esperado={}, recebido={}",
                        card.getBankAccount().getBankName(), request.bankName());
                throw new InvalidBankException("Banco inválido");
            }

            // Validar expirationDate (mínimo 6 meses no futuro)
            try {
                ExpirationDateUtil.validateAndAdjustExpirationDate(request.expirationDate());
            } catch (IllegalArgumentException e) {
                logger.error("Data de expiração inválida: {}", request.expirationDate());
                throw new InvalidExpirationDAteException(e.getMessage());
            }

            // Simular aprovação do banco (90% de chance)
            Random random = new Random();
            boolean approved = random.nextDouble() < 0.9;

            if (!approved) {
                logger.warn("Simulação de aprovação falhou para cardId={}", card.getId());
                throw new CardValidationException("Cartão recusado pela validação do banco");
            }

            logger.info("Cartão validado com sucesso: cardId={}", card.getId());
            return new CardValidateResponse(card.getId(), true);
        } catch (ResourceNotFoundException | InvalidBankException | InvalidExpirationDAteException | CardValidationException e) {
            throw e; // Re-lançar exceções específicas
        } catch (Exception e) {
            logger.error("Erro inesperado na validação do cartão: {}", e.getMessage(), e);
            throw new CardValidationException("Erro interno na validação do cartão");
        }
    }
}
