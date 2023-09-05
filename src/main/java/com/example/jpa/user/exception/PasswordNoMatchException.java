package com.example.jpa.user.exception;

public class PasswordNoMatchException extends RuntimeException {

  public PasswordNoMatchException(String s) {
    super(s);
  }
}
