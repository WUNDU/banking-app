package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.CardRequest;
import ao.com.wundu.api.dto.CardResponse;
import ao.com.wundu.infrastructure.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<CardResponse> create(@Valid @RequestBody CardRequest request) {
        CardResponse response = cardService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> findById(@PathVariable Long cardId) {
        CardResponse response = cardService.findById(cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
