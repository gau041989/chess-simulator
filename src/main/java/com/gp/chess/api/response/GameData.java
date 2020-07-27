package com.gp.chess.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class GameData {

  private final Map<String, Piece> board;
  private final List<Piece> killedPieces;

  @JsonCreator
  public GameData(
      @JsonProperty("board") Map<String, Piece> board,
      @JsonProperty("killedPieces") List<Piece> killedPieces) {
    this.board = board;
    this.killedPieces = killedPieces;
  }

  public Map<String, Piece> getBoard() {
    return board;
  }

  public List<Piece> getKilledPieces() {
    return killedPieces;
  }
}
