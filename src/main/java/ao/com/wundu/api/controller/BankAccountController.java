package ao.com.wundu.api.controller;

import ao.com.wundu.api.dto.BankAccountRequest;
import ao.com.wundu.api.dto.BankAccountResponse;
import ao.com.wundu.infrastructure.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Bank Accounts", description = "Operations related to bank accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;


    @GetMapping("/{id}")
    @Operation(summary = "Find bank account by ID", description = "Retrieves a bank account by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bank account found"),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
    })
    public ResponseEntity<BankAccountResponse> findById(
            @Parameter(description = "Bank account ID", required = true, example = "1")
            @PathVariable Long id) {
        BankAccountResponse accountResponse = accountService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @GetMapping("/{id}/decrypted")
    @Operation(summary = "Get decrypted bank account", description = "Retrieves a bank account with decrypted account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bank account retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
    })
    public ResponseEntity<BankAccountResponse> getDecrypted(
            @Parameter(description = "Bank account ID", required = true, example = "1")
            @PathVariable Long id) {
        BankAccountResponse accountResponse = accountService.getDecryptedAccountNumber(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @PostMapping
    @Operation(summary = "Create a bank account", description = "Creates a new bank account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Bank account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<BankAccountResponse> create(
            @RequestBody BankAccountRequest accountRequest) {
        BankAccountResponse accountResponse = accountService.create(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }


}
