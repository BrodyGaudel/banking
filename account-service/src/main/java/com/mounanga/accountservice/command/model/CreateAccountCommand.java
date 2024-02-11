package com.mounanga.accountservice.command.model;

import com.mounanga.accountservice.common.enums.AccountStatus;
import com.mounanga.accountservice.common.enums.Currency;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents a command for creating an account.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Getter
public class CreateAccountCommand extends BaseCommand<String>{
    private final AccountStatus status;
    private final Currency currency;
    private final BigDecimal balance;
    private final String customerId;
    private final LocalDateTime creation;

    /**
     * Constructs a new CreateAccountCommand with the given parameters.
     *
     * @param id         The identifier for the command.
     * @param status     The status of the account.
     * @param currency   The currency of the account.
     * @param balance    The balance of the account.
     * @param customerId The identifier of the customer.
     * @param creation   The creation timestamp of the account.
     */
    public CreateAccountCommand(String id, AccountStatus status, Currency currency, BigDecimal balance, String customerId, LocalDateTime creation) {
        super(id);
        this.status = status;
        this.currency = currency;
        this.balance = balance;
        this.customerId = customerId;
        this.creation = creation;
    }
}
