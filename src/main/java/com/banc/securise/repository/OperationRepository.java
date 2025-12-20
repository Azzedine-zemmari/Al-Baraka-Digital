package com.banc.securise.repository;

import com.banc.securise.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Integer> {
}
