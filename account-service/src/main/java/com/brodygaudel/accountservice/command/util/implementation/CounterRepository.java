package com.brodygaudel.accountservice.command.util.implementation;

import com.brodygaudel.accountservice.command.util.implementation.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, String> {
}
