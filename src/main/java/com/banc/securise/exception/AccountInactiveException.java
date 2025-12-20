package com.banc.securise.exception;

public class AccountInactiveException extends RuntimeException {
  public AccountInactiveException() {
    super("Account is inactive");
  }
}
