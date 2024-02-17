package com.brodygaudel.accountservice.query.restcontroller;

import com.brodygaudel.accountservice.common.exception.AccountNotFoundException;
import com.brodygaudel.accountservice.common.exception.OperationNotFoundException;
import com.brodygaudel.accountservice.query.dto.AccountDTO;
import com.brodygaudel.accountservice.query.dto.OperationDTO;
import com.brodygaudel.accountservice.query.dto.OperationPageDTO;
import com.brodygaudel.accountservice.query.model.GetAccountByCustomerIdQuery;
import com.brodygaudel.accountservice.query.model.GetAccountByIdQuery;
import com.brodygaudel.accountservice.query.model.GetOperationByIdQuery;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/queries")
public class AccountQueryRestController {

    private final QueryGateway queryGateway;

    public AccountQueryRestController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/get-account/{id}")
    public AccountDTO getAccountById(@PathVariable String id){
        GetAccountByIdQuery query = new GetAccountByIdQuery(id);
        ResponseType<AccountDTO> responseType = ResponseTypes.instanceOf(AccountDTO.class);
        return queryGateway.query(query , responseType).join();
    }

    @GetMapping("/find-account/{customerId}")
    public AccountDTO getAccountByCustomerId(@PathVariable String customerId){
        GetAccountByCustomerIdQuery query = new GetAccountByCustomerIdQuery(customerId);
        ResponseType<AccountDTO> responseType = ResponseTypes.instanceOf(AccountDTO.class);
        return queryGateway.query(query , responseType).join();
    }

    @GetMapping("/get-operation/{id}")
    public OperationDTO getOperationById(@PathVariable String id){
        GetOperationByIdQuery query = new GetOperationByIdQuery(id);
        ResponseType<OperationDTO> responseType = ResponseTypes.instanceOf(OperationDTO.class);
        return queryGateway.query(query , responseType).join();
    }

    @GetMapping("/find-operation/{accountId}")
    public OperationPageDTO getOperationsByAccountId(@PathVariable String accountId){
        GetOperationByIdQuery query = new GetOperationByIdQuery(accountId);
        ResponseType<OperationPageDTO> responseType = ResponseTypes.instanceOf(OperationPageDTO.class);
        return queryGateway.query(query , responseType).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception) {
        HttpStatus httpStatus;
        if(exception instanceof AccountNotFoundException || exception instanceof OperationNotFoundException){
            httpStatus = HttpStatus.NOT_FOUND;
        } else{
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(exception.getMessage(), httpStatus);
    }
}
