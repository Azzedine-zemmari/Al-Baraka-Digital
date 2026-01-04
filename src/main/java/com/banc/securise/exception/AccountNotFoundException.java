package com.banc.securise.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(){
        super("account nt found");
    }
    
}
