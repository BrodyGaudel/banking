package com.mounanga.accountservice.command.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents a command for debiting an account.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Getter
public class DebitAccountCommand extends BaseCommand<String> {
    private final String description;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;

    /**
     * Constructs a new DebitAccountCommand with the given parameters.
     *
     * @param id          The identifier for the command.
     * @param description The description of the debit transaction.
     * @param amount      The amount to be debited.
     * @param dateTime    The date and time of the debit transaction.
     */
    public DebitAccountCommand(String id, String description, BigDecimal amount, LocalDateTime dateTime) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.dateTime = dateTime;
    }
}
