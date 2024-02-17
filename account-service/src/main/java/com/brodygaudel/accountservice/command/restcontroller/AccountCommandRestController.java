package com.brodygaudel.accountservice.command.restcontroller;

import com.brodygaudel.accountservice.command.dto.CreateAccountDTO;
import com.brodygaudel.accountservice.command.dto.CreditDTO;
import com.brodygaudel.accountservice.command.dto.DebitDTO;
import com.brodygaudel.accountservice.command.dto.UpdateStatusDTO;
import com.brodygaudel.accountservice.command.service.AccountCommandService;
import com.brodygaudel.accountservice.common.exception.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception) {
        HttpStatus httpStatus;
        if (exception instanceof AccountNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        }  else if (exception instanceof AmountNotSufficientException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exception instanceof BalanceNotInsufficientException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exception instanceof AccountNotActivatedException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exception instanceof CustomerAlreadyHaveAccountException) {
            httpStatus = HttpStatus.CONFLICT;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(exception.getMessage(), httpStatus);
    }
}

