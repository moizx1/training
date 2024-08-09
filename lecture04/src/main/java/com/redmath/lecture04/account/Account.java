package com.redmath.lecture04.account;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="accounts")
public class Account {

    @Id
    private Long accountId;
//    private Long userId;
    private String accountType;
    private double balance;
    private LocalDateTime createdAt;
}
