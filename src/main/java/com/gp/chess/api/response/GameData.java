package com.gp.chess.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class GameData {

  private final Map<String, Piece> board;

  @JsonCreator
  public GameData(@JsonProperty("board") Map<String, Piece> board) {
    this.board = board;
  }

  public Map<String, Piece> getBoard() {
    return board;
  }
}
