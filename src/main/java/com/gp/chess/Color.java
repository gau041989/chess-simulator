package com.gp.chess;

public enum Color {
  BLACK,
  WHITE;

  public Color getOtherColor() {
    return this.equals(WHITE) ? BLACK : WHITE;
  }
}