package com.banc.securise.entity;

import com.banc.securise.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="email",unique = true)
    private String email;
    
    private String password;
    
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CLIENT;

    private String active = "true";

    private LocalDateTime created_at = LocalDateTime.now();

}
