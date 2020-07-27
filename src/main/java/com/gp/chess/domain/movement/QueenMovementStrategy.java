package com.gp.chess.domain.movement;

import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class QueenMovementStrategy extends MovementStrategy {

  private final RookMovementStrategy rookMovementStrategy;
  private final BishopMovementStrategy bishopMovementStrategy;

  public QueenMovementStrategy(Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill,
      RookMovementStrategy rookMovementStrategy,
      BishopMovementStrategy bishopMovementStrategy) {
    super(canOccupy, canKill);
    this.rookMovementStrategy = rookMovementStrategy;
    this.bishopMovementStrategy = bishopMovementStrategy;
  }

  @Override
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    List<BoardAction> possibleDiagonalMoves = bishopMovementStrategy.getPossibleMoves(piece, position);
    List<BoardAction> possibleXYMoves = rookMovementStrategy.getPossibleMoves(piece, position);

    return new ArrayList<>(){{
      addAll(possibleDiagonalMoves);
      addAll(possibleXYMoves);
    }};
  }
}
