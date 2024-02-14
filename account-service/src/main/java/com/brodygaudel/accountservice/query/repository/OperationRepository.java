package com.brodygaudel.accountservice.query.repository;

import com.brodygaudel.accountservice.query.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
