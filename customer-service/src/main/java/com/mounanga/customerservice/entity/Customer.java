package com.mounanga.customerservice.entity;

import com.mounanga.customerservice.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a customer entity.
 *
 * @author Brody Gaudel MOUNANGA BOUKA
 * @since 2024
 * @version 3.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 6, max = 16)
    private String cin;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String firstname;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String placeOfBirth;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String nationality;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    private LocalDateTime lastUpdated;
}

