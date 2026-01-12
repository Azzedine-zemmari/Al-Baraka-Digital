package com.banc.securise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    @Min(value = 0)
    private Double balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false,unique=true)
    private User owner;

    @OneToMany(mappedBy = "accountSource", fetch = FetchType.LAZY)
    private List<Operation> outgoingOperations;

    @OneToMany(mappedBy = "accountDestination", fetch = FetchType.LAZY)
    private List<Operation> incomingOperations;

}
