package com.brodygaudel.accountservice.command.restcontroller;

import com.brodygaudel.accountservice.command.dto.CreateAccountDTO;
import com.brodygaudel.accountservice.command.dto.CreditDTO;
import com.brodygaudel.accountservice.command.dto.DebitDTO;
import com.brodygaudel.accountservice.command.dto.UpdateStatusDTO;
import com.brodygaudel.accountservice.command.service.AccountCommandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts/commands")
public class AccountCommandRestController {

    private final AccountCommandService accountCommandService;

    @Autowired
    public AccountCommandRestController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<String>> createAccount(@RequestBody CreateAccountDTO dto) {
        return accountCommandService.create(dto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error creating account: " + ex.getMessage()));
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<String>> updateAccountStatus(@RequestBody UpdateStatusDTO dto) {
        return accountCommandService.update(dto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error updating account status: " + ex.getMessage()));
    }

    @PutMapping("/credit")
    public CompletableFuture<ResponseEntity<String>> creditAccount(@RequestBody CreditDTO dto) {
        return accountCommandService.credit(dto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error crediting account: " + ex.getMessage()));
    }

    @PutMapping("/debit")
    public CompletableFuture<ResponseEntity<String>> debitAccount(@RequestBody DebitDTO dto) {
        return accountCommandService.debit(dto)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error debiting account: " + ex.getMessage()));
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteAccount(@PathVariable String id) {
        return accountCommandService.delete(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error deleting account: " + ex.getMessage()));
    }

}

