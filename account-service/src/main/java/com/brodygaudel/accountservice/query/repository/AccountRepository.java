package com.brodygaudel.accountservice.query.repository;

import com.brodygaudel.accountservice.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("select a from Account a where a.customerId =?1")
    Optional<Account> findByCustomerId(String customerId);
}
