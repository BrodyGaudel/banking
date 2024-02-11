package com.mounanga.accountservice.command.model;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * This class represents a base command with an identifier.
 *
 * @param <T> The type of identifier.
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Getter
public class BaseCommand<T> {

    /**
     * The identifier for the command.
     */
    @TargetAggregateIdentifier
    private final T id;

    /**
     * Constructs a new BaseCommand with the given identifier.
     *
     * @param id The identifier for the command.
     */
    public BaseCommand(T id) {
        this.id = id;
    }
}
