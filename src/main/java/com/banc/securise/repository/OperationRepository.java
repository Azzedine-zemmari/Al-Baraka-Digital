package com.banc.securise.repository;

import com.banc.securise.entity.Operation;
import com.banc.securise.enums.OperationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Integer> {
    List<Operation> findByStatus(OperationStatus status);
}
