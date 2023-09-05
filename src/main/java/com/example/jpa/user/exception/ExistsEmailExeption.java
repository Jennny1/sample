package com.example.jpa.user.exception;

public class ExistsEmailExeption extends RuntimeException {
  public ExistsEmailExeption(String s){
    super(s);

  }
}
