package ao.com.wundu.infrastructure.service.impl;

import ao.com.wundu.api.dto.TransactionMessageDTO;
import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.domain.model.Card;
import ao.com.wundu.domain.model.Transaction;
import ao.com.wundu.infrastructure.exception.ResourceNotFoundException;
import ao.com.wundu.infrastructure.repository.BankAccountRepository;
import ao.com.wundu.infrastructure.repository.CardRepository;
import ao.com.wundu.infrastructure.repository.TransactionRepository;
import ao.com.wundu.infrastructure.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    @Transactional
    public TransactionResponse create(TransactionRequest request) {
        logger.info("Criando transação para accountId: {}", request.accountId());


        BankAccount account = accountRepository.findById(request.accountId())
                .orElseThrow( () ->  {
                    logger.error("Conta não encontrada: {}", request.accountId());
                    return new ResourceNotFoundException("Conta não encontrada!!");
                });

        Card card = null;
        if (request.accountId() != null ) {
            card = cardRepository.findById(request.cardId())
                    .orElseThrow( () -> {
                        logger.error("Cartão não encontrado: {}", request.cardId());
                        return new ResourceNotFoundException("Cartão não encontrado");
                    });
            if (!card.getBankAccount().getId().equals(request.accountId())) {
                logger.error("Cartão {} não pertence à conta {}", request.cardId(), request.accountId());
                throw new ResourceNotFoundException("Cartão não pertence à conta especificada");
            }
        }

        // Criar transação
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setMerchant(request.merchant());
        transaction.setType(request.type());
        transaction.setDateTime(request.dateTime());
        transaction.setDescription(request.description());
        transaction.setBankAccount(account);
        transaction.setCard(card);

        transaction = transactionRepository.save(transaction);
        logger.info("Transação criada: transactionId={}", transaction.getId());

        try {
            TransactionMessageDTO event = new TransactionMessageDTO(
                    transaction.getCard() != null ? transaction.getCard().getId() : null,
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getType(),
                    transaction.getDateTime()
            );
            rabbitTemplate.convertAndSend("transaction-exchange", "transaction.created", event);
            logger.info("Evento transaction.created publicado para transactionId={}", transaction.getId());
        } catch (Exception e) {
            logger.error("Falha ao publicar evento para transactionId={}: {}", transaction.getId(), e.getMessage());
        }


        return new TransactionResponse(
                transaction.getId(),
                transaction.getBankAccount().getId(),
                transaction.getCard() != null ? transaction.getCard().getId() : null,
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getType(),
                transaction.getDateTime(),
                transaction.getDescription()
        );
    }

    @Override
    public Page<TransactionResponse> listTransactionsByCard(Long cardId, Pageable pageable) {
        logger.info("Listando Transações para cardId: {}", cardId);

        if (!cardRepository.existsById(cardId)) {
            logger.error("Cartão não encontrado: {}", cardId);
            throw new ResourceNotFoundException("Cartão não encontrado");
        }

        Page<Transaction> transactions = transactionRepository.findByCardId(cardId, pageable);
        return transactions.map(t -> new TransactionResponse(
                t.getId(),
                t.getBankAccount().getId(),
                t.getCard() != null ? t.getCard().getId() : null,
                t.getAmount(),
                t.getMerchant(),
                t.getType(),
                t.getDateTime(),
                t.getDescription()
        ));
    }

}
