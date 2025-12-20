package com.banc.securise.exception;

public class AccountInactiveException extends RuntimeException {
  public AccountInactiveException(String message) {
    super(message);
  }
}
