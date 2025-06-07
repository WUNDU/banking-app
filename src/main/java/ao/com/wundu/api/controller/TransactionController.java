package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.TransactionEventDTO;
import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import ao.com.wundu.infrastructure.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Transactions", description = "Operations related to financial transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a transaction", description = "Creates a new financial transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        logger.info("Recebendo requisição para criar transação: accountId={}", request.accountId());
        TransactionResponse response = transactionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/card/{cardId}")
    @Operation(summary = "List transactions by card", description = "Retrieves paginated transactions for a specific card")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public ResponseEntity<Page<TransactionResponse>> listTransactionsByCard(
            @Parameter(description = "Card ID", required = true, example = "1")
            @PathVariable Long cardId,
            @Parameter(description = "Pagination details") Pageable pageable) {
        logger.info("Recebendo requisição para listar transações: cardId={}", cardId);
        Page<TransactionResponse> transactions = transactionService.listTransactionsByCard(cardId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/card/{cardId}/transactions")
    @Operation(summary = "List transactions for Finances API", description = "Retrieves transactions for a card, formatted for Wundu Finances API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public ResponseEntity<List<TransactionEventDTO>> listTransactionsForFinances(
            @Parameter(description = "Card ID", required = true, example = "1")
            @PathVariable Long cardId) {
        logger.info("Recebendo requisição para listar transações para Finances API: cardId={}", cardId);
        List<TransactionEventDTO> transactions = transactionService.listTransactionsForFinances(cardId);
        return ResponseEntity.ok(transactions);
    }
}
