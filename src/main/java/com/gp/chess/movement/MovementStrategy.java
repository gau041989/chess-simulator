package com.gp.chess.movement;

import com.gp.chess.Piece;
import com.gp.chess.Position;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class MovementStrategy {

  protected final Predicate<Position> canOccupy;
  protected final BiPredicate<Piece, Position> canKill;

  public MovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    this.canOccupy = canOccupy;
    this.canKill = canKill;
  }

  public abstract List<Position> getPossibleMoves(Piece piece, Position position);

}
