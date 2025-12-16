package com.banc.securise.entity;

import com.banc.securise.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    private int Id;

    @Column(name="email",unique = true)
    private String email;
    
    private String password;
    
    private String fullName;
    
    private Role role;

    private Boolean active;

    private LocalDateTime created_at;

}
