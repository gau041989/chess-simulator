package com.gp.chess.movement;

import com.gp.chess.Piece;
import com.gp.chess.Position;
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
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    List<Position> possibleDiagonalMoves = bishopMovementStrategy.getPossibleMoves(piece, position);
    List<Position> possibleXYMoves = rookMovementStrategy.getPossibleMoves(piece, position);

    return new ArrayList<>(){{
      addAll(possibleDiagonalMoves);
      addAll(possibleXYMoves);
    }};
  }
}
