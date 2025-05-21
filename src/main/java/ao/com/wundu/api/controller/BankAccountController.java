package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.BankAccountRequest;
import ao.com.wundu.api.dto.BankAccountResponse;
import ao.com.wundu.infrastructure.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;


    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> findById(@PathVariable Long id) {

        BankAccountResponse accountResponse = accountService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(accountResponse);
    }

    @GetMapping("/{id}/decrypted")
    public ResponseEntity<BankAccountResponse> getDecrypted(@PathVariable Long id) {
        BankAccountResponse accountResponse = accountService.getDecryptedAccountNumber(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(accountResponse);
    }

    @PostMapping
    public ResponseEntity<BankAccountResponse> create(@RequestBody BankAccountRequest accountRequest) {

        BankAccountResponse accountResponse = accountService.create(accountRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountResponse);
    }


}
