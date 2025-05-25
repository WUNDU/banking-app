package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import ao.com.wundu.infrastructure.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        logger.info("Recebendo requisição para criar transação: accountId={}", request.accountId());
        TransactionResponse response = transactionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<Page<TransactionResponse>> listTransactionsByCard(
            @PathVariable Long cardId, Pageable pageable) {
        logger.info("Recebendo requisição para listar transações: cardId={}", cardId);
        Page<TransactionResponse> transactions = transactionService.listTransactionsByCard(cardId, pageable);
        return ResponseEntity.ok(transactions);
    }
}
