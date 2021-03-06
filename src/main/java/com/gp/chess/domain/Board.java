package com.gp.chess.domain;

import static com.gp.chess.domain.action.ActionType.KILL;
import static com.gp.chess.domain.action.ActionType.OCCUPY;

import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import com.gp.chess.domain.character.PieceType;
import com.gp.chess.domain.exception.InvalidMoveException;
import com.gp.chess.domain.movement.BishopMovementStrategy;
import com.gp.chess.domain.movement.KingMovementStrategy;
import com.gp.chess.domain.movement.KnightMovementStrategy;
import com.gp.chess.domain.movement.MovementStrategy;
import com.gp.chess.domain.movement.PawnMovementStrategy;
import com.gp.chess.domain.movement.QueenMovementStrategy;
import com.gp.chess.domain.movement.RookMovementStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Board {

  private final Map<Position, Piece> piecePositions = new HashMap<>();
  private final List<Piece> killedPiecePositions = new ArrayList<>();
  private final Map<PieceType, MovementStrategy> pieceMovementStrategies;

  private final Predicate<Position> canOccupy = position -> !piecePositions.containsKey(position);

  private final BiPredicate<Piece, Position> canKill = (piece, position) ->
      piecePositions.containsKey(position)
          && piecePositions.get(position).getColor().equals(piece.getColor().getOtherColor());

  private Board(Map<Position, Piece> piecePositions) {
    this.piecePositions.putAll(piecePositions);
    this.pieceMovementStrategies = new HashMap<>() {{
      RookMovementStrategy rookMovementStrategy = new RookMovementStrategy(canOccupy, canKill);
      BishopMovementStrategy bishopMovementStrategy = new BishopMovementStrategy(canOccupy, canKill);

      put(PieceType.PAWN, new PawnMovementStrategy(canOccupy, canKill));
      put(PieceType.ROOK, rookMovementStrategy);
      put(PieceType.BISHOP, bishopMovementStrategy);
      put(PieceType.QUEEN, new QueenMovementStrategy(canOccupy, canKill, rookMovementStrategy, bishopMovementStrategy));
      put(PieceType.KING, new KingMovementStrategy(canOccupy, canKill));
      put(PieceType.KNIGHT, new KnightMovementStrategy(canOccupy, canKill));
    }};
  }

  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    Piece actualPiece = piecePositions.get(position);
    if (actualPiece.equals(piece)) {
      return pieceMovementStrategies
          .get(piece.getPieceType())
          .getPossibleMoves(piece, position);
    }
    return Collections.emptyList();
  }

  public Map<Position, Piece> movePiece(Position from, Position to) {
    Piece piece = piecePositions.get(from);
    List<BoardAction> possibleMoves = getPossibleMoves(piece, from);
    return possibleMoves.stream()
        .filter(ba -> ba.getPosition().equals(to))
        .findFirst()
        .map(boardAction -> movePiece(piece, from, boardAction))
        .orElseThrow(InvalidMoveException::new);
  }

  private Map<Position, Piece> movePiece(Piece piece, Position from, BoardAction boardAction) {
    Position to = boardAction.getPosition();
    if(boardAction.getActionType().equals(OCCUPY)) {
      piecePositions.remove(from);
      piecePositions.put(to, piece);
    }

    if(boardAction.getActionType().equals(KILL)) {
      killedPiecePositions.add(piecePositions.remove(to));
      piecePositions.remove(from);
      piecePositions.put(to, piece);
    }
    return getPiecePositions();
  }

  Map<Position, Piece> getPiecePositions() {
    return Collections.unmodifiableMap(piecePositions);
  }

  List<Piece> getKilledPieces() {
    return Collections.unmodifiableList(killedPiecePositions);
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
