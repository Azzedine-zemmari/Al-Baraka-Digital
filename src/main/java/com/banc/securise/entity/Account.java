package com.banc.securise.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private Double balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false,unique=true)
    private User owner;

    @OneToMany(mappedBy = "accountSource", fetch = FetchType.LAZY)
    private List<Operation> outgoingOperations;

    @OneToMany(mappedBy = "accountDestination", fetch = FetchType.LAZY)
    private List<Operation> incomingOperations;

}
