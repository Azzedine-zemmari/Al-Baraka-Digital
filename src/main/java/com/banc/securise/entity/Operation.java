package com.banc.securise.entity;

import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Enumerated(EnumType.STRING)
    private OperationStatus status;

     private LocalDateTime createdAt;

     private LocalDateTime validatedAt;

     private LocalDateTime executedAt;

     @ManyToOne
     @JoinColumn(name="accountSource_id",nullable=true)
     private Account accountSource;

    @ManyToOne
    @JoinColumn(name="accountDestination_id",nullable=true)
    private Account accountDestination;



}
