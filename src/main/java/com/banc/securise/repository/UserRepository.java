package com.banc.securise.repository;

import com.banc.securise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("UPDATE User u SET u.active = :active where u.id = :id")
    Integer updateStatu(@Param("active") String active , @Param("id") Integer id);
}
