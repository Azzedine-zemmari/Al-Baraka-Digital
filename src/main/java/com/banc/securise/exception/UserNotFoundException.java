package com.banc.securise.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("user not found");
  }
}
