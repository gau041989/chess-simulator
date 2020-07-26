package com.gp.chess.domain.character;

public enum Color {
  BLACK,
  WHITE;

  public Color getOtherColor() {
    return this.equals(WHITE) ? BLACK : WHITE;
  }
}