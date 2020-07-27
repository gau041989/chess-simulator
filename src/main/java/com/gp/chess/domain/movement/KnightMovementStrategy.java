package com.gp.chess.domain.movement;

import static java.util.Arrays.asList;

import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.cell.Traversal;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class KnightMovementStrategy extends MovementStrategy {

  public KnightMovementStrategy(Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    Traversal<Row> row = position.getRow();
    Traversal<Column> column = position.getColumn();

    Optional<Traversal<Row>> nextNextRow = tryGet2Step(position, p -> p.getRow().next(), fromRow);
    Optional<BoardAction> move1 = tryToMakeAMove(piece, nextNextRow, column.next());
    Optional<BoardAction> move2 = tryToMakeAMove(piece, nextNextRow, column.prev());

    Optional<Traversal<Column>> nextNextCol = tryGet2Step(position, p -> p.getColumn().next(), fromCol);
    Optional<BoardAction> move3 = tryToMakeAMove(piece, row.next(), nextNextCol);
    Optional<BoardAction> move4 = tryToMakeAMove(piece, row.prev(), nextNextCol);

    Optional<Traversal<Row>> prevPrevRow = tryGet2Step(position, p -> p.getRow().prev(), fromRow);
    Optional<BoardAction> move5 = tryToMakeAMove(piece, prevPrevRow, column.next());
    Optional<BoardAction> move6 = tryToMakeAMove(piece, prevPrevRow, column.prev());

    Optional<Traversal<Column>> prevPrevCol = tryGet2Step(position, p -> p.getColumn().prev(), fromCol);
    Optional<BoardAction> move7 = tryToMakeAMove(piece, row.next(), prevPrevCol);
    Optional<BoardAction> move8 = tryToMakeAMove(piece, row.prev(), prevPrevCol);

    return getAvailableMoves(asList(move1, move2, move3, move4, move5, move6, move7, move8));
  }

  private <T extends Traversal<T>> Optional<Traversal<T>> tryGet2Step(Position position,
      Function<Position, Optional<Traversal<T>>> traversalFn,
      BiFunction<Position, Traversal<T>, Position> fromTraversal) {
    Optional<Traversal<T>> traversal = traversalFn.apply(position);
    return traversal.flatMap(t -> {
      Position newPosition = fromTraversal.apply(position, t);
      return traversalFn.apply(newPosition);
    });
  }
}
