package com.gp.chess.domain.movement;

import static java.util.Arrays.asList;

import com.gp.chess.domain.action.BoardAction;
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
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    Optional<BoardAction> nextOp = tryToMakeAMove(piece, position.getRow().next(), Optional.of(position.getColumn()));
    Optional<BoardAction> prevOp = tryToMakeAMove(piece, position.getRow().prev(), Optional.of(position.getColumn()));
    Optional<BoardAction> leftOp = tryToMakeAMove(piece, Optional.of(position.getRow()), position.getColumn().prev());
    Optional<BoardAction> rightOp = tryToMakeAMove(piece, Optional.of(position.getRow()), position.getColumn().next());
    Optional<BoardAction> rightUpDiagOp = tryToMakeAMove(piece, position.getRow().next(), position.getColumn().next());
    Optional<BoardAction> rightDownDiagOp = tryToMakeAMove(piece, position.getRow().prev(), position.getColumn().prev());
    Optional<BoardAction> leftUpDiagOp = tryToMakeAMove(piece, position.getRow().next(), position.getColumn().prev());
    Optional<BoardAction> leftDownDiagOp = tryToMakeAMove(piece, position.getRow().prev(), position.getColumn().next());

    return getAvailableMoves(asList(nextOp, prevOp, leftOp, rightOp, rightUpDiagOp, rightDownDiagOp,
        leftUpDiagOp, leftDownDiagOp));
  }
}
