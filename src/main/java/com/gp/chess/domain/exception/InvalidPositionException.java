package com.gp.chess.domain.exception;

public class InvalidPositionException extends DomainException{

  public InvalidPositionException() {
    super("Invalid position");
  }
}
