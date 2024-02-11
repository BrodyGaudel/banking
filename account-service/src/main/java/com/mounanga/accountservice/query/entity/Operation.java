package com.mounanga.accountservice.query.entity;

import com.mounanga.accountservice.common.enums.Currency;
import com.mounanga.accountservice.common.enums.OperationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false, updatable = false)
    @NotBlank
    @Size(min = 1, max = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
