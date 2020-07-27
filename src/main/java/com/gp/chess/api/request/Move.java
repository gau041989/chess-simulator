package com.gp.chess.api.request;

public class Move {
  private final String source;
  private final String destination;

  public Move(String source, String destination) {
    this.source = source;
    this.destination = destination;
  }

  public String getDestination() {
    return destination;
  }

  public String getSource() {
    return source;
  }
}
