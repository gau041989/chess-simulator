package com.gp.chess.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Piece {
  private final String color;
  private final String type;
  private final List<String> possibleMoves;

  @JsonCreator
  public Piece(
      @JsonProperty("color") String color,
      @JsonProperty("type") String type,
      @JsonProperty("possibleMoves") List<String> possibleMoves) {
    this.color = color;
    this.type = type;
    this.possibleMoves = possibleMoves;
  }

  public String getColor() {
    return color;
  }

  public String getType() {
    return type;
  }

  public List<String> getPossibleMoves() {
    return possibleMoves;
  }
}
