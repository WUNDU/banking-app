package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.*;
import ao.com.wundu.infrastructure.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
@Tag(name = "Cards", description = "Operations related to credit/debit cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;


    @PostMapping
    @Operation(summary = "Create a card", description = "Creates a new credit or debit card")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CardResponse> create(
            @Valid @RequestBody CardRequest request) {
        logger.info("Recebendo requisição para criar cartão");
        CardResponse response = cardService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Find card by ID", description = "Retrieves a card by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card found"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public ResponseEntity<CardResponse> findById(
            @Parameter(description = "Card ID", required = true, example = "1")
            @PathVariable Long cardId) {
        logger.info("Recebendo requisição para buscar cartão: cardId={}", cardId);
        CardResponse response = cardService.findById(cardId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/decrypted")
    @Operation(summary = "Get decrypted card", description = "Retrieves a card with decrypted card number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public ResponseEntity<CardResponse> getDecrypted(
            @Parameter(description = "Card ID", required = true, example = "1")
            @PathVariable Long id) {
        CardResponse cardResponse = cardService.getDecryptedAccountNumber(id);
        return ResponseEntity.status(HttpStatus.OK).body(cardResponse);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate a card", description = "Validates a card's details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card validated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid card details")
    })
    public ResponseEntity<CardValidateResponse> validateCard(
            @Valid @RequestBody CardValidateRequest request) {
        logger.info("Recebendo requisição para validar cartão: cardNumber=****{}",
                request.cardNumber().substring(12));
        CardValidateResponse response = cardService.validateCard(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
