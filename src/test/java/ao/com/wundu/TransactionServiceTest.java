package ao.com.wundu;

import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.domain.model.Card;
import ao.com.wundu.domain.model.Transaction;
import ao.com.wundu.infrastructure.exception.ResourceNotFoundException;
import ao.com.wundu.infrastructure.repository.BankAccountRepository;
import ao.com.wundu.infrastructure.repository.CardRepository;
import ao.com.wundu.infrastructure.repository.TransactionRepository;
import ao.com.wundu.infrastructure.service.impl.TransactionServiceImpl;
import ao.com.wundu.util.AesEncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BankAccountRepository accountRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private AesEncryptionUtil aesEncryptionUtil;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction_success() {
        BankAccount account = new BankAccount();
        account.setId(1L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Card card = new Card();
        card.setId(1L);
        card.setBankAccount(account);
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        TransactionRequest dto = new TransactionRequest(
                1L, 1L, 100.0, "Shoprite", "COMPRA", LocalDateTime.now(), "Compra de alimentos"
        );
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBankAccount(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.create(dto);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void createTransaction_accountNotFound_throwsException() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        TransactionRequest dto = new TransactionRequest(
                null, 1L, 100.0, "Shoprite", "COMPRA", LocalDateTime.now(), null
        );

        assertThrows(ResourceNotFoundException.class, () -> transactionService.create(dto));
    }

    @Test
    void listTransactionsByCard_success() {
        when(cardRepository.existsById(1L)).thenReturn(true);
        Page<Transaction> page = Page.empty();
        when(transactionRepository.findByCardId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<TransactionResponse> result = transactionService.listTransactionsByCard(1L, Pageable.unpaged());

        assertNotNull(result);
        verify(transactionRepository).findByCardId(eq(1L), any(Pageable.class));
    }
}
