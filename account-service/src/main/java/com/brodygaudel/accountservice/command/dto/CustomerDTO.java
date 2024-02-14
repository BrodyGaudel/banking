package com.brodygaudel.accountservice.command.dto;

import com.brodygaudel.accountservice.common.enums.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a response DTO for retrieving customer details.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
public record CustomerDTO(
        String id,
        String cin,
        String firstname,
        String name,
        LocalDate dateOfBirth,
        String placeOfBirth,
        String nationality,
        Sex sex,
        String email,
        LocalDateTime creation,
        LocalDateTime lastUpdated) {
}
