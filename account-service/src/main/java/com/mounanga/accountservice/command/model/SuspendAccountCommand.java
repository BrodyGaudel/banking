package com.mounanga.accountservice.command.model;

import com.mounanga.accountservice.common.enums.AccountStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * This class represents a command for suspending an account.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Getter
public class SuspendAccountCommand extends BaseCommand<String>{

    private final AccountStatus status;
    private final LocalDateTime dateTime;

    /**
     * Constructs a new SuspendAccountCommand with the given parameters.
     *
     * @param id       The identifier for the command.
     * @param status   The status of the account.
     * @param dateTime The date and time of the suspension.
     */
    public SuspendAccountCommand(String id, AccountStatus status, LocalDateTime dateTime) {
        super(id);
        this.status = status;
        this.dateTime = dateTime;
    }
}
