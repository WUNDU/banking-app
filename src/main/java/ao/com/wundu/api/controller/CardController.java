package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.*;
import ao.com.wundu.infrastructure.service.CardService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<CardResponse> create(@Valid @RequestBody CardRequest request) {
        logger.info("Recebendo requisição para criar cartão");
        CardResponse response = cardService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> findById(@PathVariable Long cardId) {
        logger.info("Recebendo requisição para buscar cartão: cardId={}", cardId);
        CardResponse response = cardService.findById(cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}/decrypted")
    public ResponseEntity<CardResponse> getDecrypted(@PathVariable Long id) {
        CardResponse cardResponse = cardService.getDecryptedAccountNumber(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(cardResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<CardValidateResponse> validateCard(@Valid @RequestBody CardValidateRequest request) {
        logger.info("Recebendo requisição para validar cartão: cardNumber=****{}",
                request.cardNumber().substring(12));
        CardValidateResponse response = cardService.validateCard(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
