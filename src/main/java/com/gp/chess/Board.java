package com.gp.chess;

import com.gp.chess.movement.BishopMovementStrategy;
import com.gp.chess.movement.MovementStrategy;
import com.gp.chess.movement.PawnMovementStrategy;
import com.gp.chess.movement.RookMovementStrategy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Board {

  private final Map<Position, Piece> piecePositions = new HashMap<>();
  private final Map<PieceType, MovementStrategy> pieceMovementStrategies;

  private final Predicate<Position> canOccupy = position -> !piecePositions.containsKey(position);

  private final BiPredicate<Piece, Position> canKill = (piece, position) ->
      piecePositions.containsKey(position)
          && piecePositions.get(position).getColor().equals(piece.getColor().getOtherColor());

  private Board(Map<Position, Piece> piecePositions) {
    this.piecePositions.putAll(piecePositions);
    this.pieceMovementStrategies = new HashMap<>() {{
      put(PieceType.PAWN, new PawnMovementStrategy(canOccupy, canKill));
      put(PieceType.ROOK, new RookMovementStrategy(canOccupy, canKill));
      put(PieceType.BISHOP, new BishopMovementStrategy(canOccupy, canKill));
    }};
  }

  public List<Position> getPossibleMoves(Piece piece, Position position) {
    Piece actualPiece = piecePositions.get(position);
    if (actualPiece.equals(piece)) {
      return pieceMovementStrategies
          .get(piece.getPieceType())
          .getPossibleMoves(piece, position);
    }
    return Collections.emptyList();
  }

  public static class BoardBuilder {

    private final Map<Position, Piece> piecePositions;

    public BoardBuilder() {
      this.piecePositions = new HashMap<>();
    }

    public BoardBuilder withPiece(Piece piece, Position position) {
      this.piecePositions.put(position, piece);
      return this;
    }

    public Board build() {
      return new Board(this.piecePositions);
    }
  }
}
