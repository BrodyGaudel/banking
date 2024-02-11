package com.mounanga.customerservice.dto;

import com.mounanga.customerservice.enums.Sex;

import java.time.LocalDate;

/**
 * Represents a request DTO for creating or updating a customer.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
public record CustomerRequestDTO(
        String cin,
        String firstname,
        String name,
        LocalDate dateOfBirth,
        String placeOfBirth,
        String nationality,
        Sex sex,
        String email) {
}
