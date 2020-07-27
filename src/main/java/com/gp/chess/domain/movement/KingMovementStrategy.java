package com.gp.chess.domain.movement;

import static java.util.Arrays.asList;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class KingMovementStrategy extends MovementStrategy {

  public KingMovementStrategy(Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    Optional<Position> nextOp = tryToGetPosition(piece, position.getRow().next(), Optional.of(position.getColumn()));
    Optional<Position> prevOp = tryToGetPosition(piece, position.getRow().prev(), Optional.of(position.getColumn()));
    Optional<Position> leftOp = tryToGetPosition(piece, Optional.of(position.getRow()), position.getColumn().prev());
    Optional<Position> rightOp = tryToGetPosition(piece, Optional.of(position.getRow()), position.getColumn().next());
    Optional<Position> rightUpDiagOp = tryToGetPosition(piece, position.getRow().next(), position.getColumn().next());
    Optional<Position> rightDownDiagOp = tryToGetPosition(piece, position.getRow().prev(), position.getColumn().prev());
    Optional<Position> leftUpDiagOp = tryToGetPosition(piece, position.getRow().next(), position.getColumn().prev());
    Optional<Position> leftDownDiagOp = tryToGetPosition(piece, position.getRow().prev(), position.getColumn().next());

    return getAvailablePositions(asList(nextOp, prevOp, leftOp, rightOp, rightUpDiagOp, rightDownDiagOp,
        leftUpDiagOp, leftDownDiagOp));
  }
}
