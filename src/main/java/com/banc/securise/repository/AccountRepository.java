package com.banc.securise.repository;

import com.banc.securise.entity.Account;
import com.banc.securise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByOwner(User owner);
}
