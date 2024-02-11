package com.mounanga.accountservice.command.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents a command for crediting an account.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Getter
public class CreditAccountCommand extends BaseCommand<String> {

    private final String description;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;

    /**
     * Constructs a new CreditAccountCommand with the given parameters.
     *
     * @param id          The identifier for the command.
     * @param description The description of the credit transaction.
     * @param amount      The amount to be credited.
     * @param dateTime    The date and time of the credit transaction.
     */
    public CreditAccountCommand(String id, String description, BigDecimal amount, LocalDateTime dateTime) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.dateTime = dateTime;
    }
}
