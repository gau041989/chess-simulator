package com.gp.chess.domain.exception;

public class InvalidMoveException extends DomainException {

  public InvalidMoveException() {
    super("Invalid move");
  }
}
