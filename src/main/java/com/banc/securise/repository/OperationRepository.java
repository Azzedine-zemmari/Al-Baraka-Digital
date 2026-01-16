package com.banc.securise.repository;

import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.enums.OperationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation,Integer> {
    List<Operation> findByStatus(OperationStatus status);
    @Query("SELECT o FROM Operation o WHERE o.accountSource = :account OR o.accountDestination = :account")
    List<Operation> findOperationsByAccount(@Param("account") Account account);
    @Query("SELECT o FROM Operation o WHERE (o.accountSource = :account OR o.accountDestination=:account) AND o.amount > :minAmount AND o.status = com.banc.securise.enums.OperationStatus.PENDING")
    List<Operation> findOperationsNotjustified(@Param("account") Account account , @Param("minAmount") Double minAmount);
}
