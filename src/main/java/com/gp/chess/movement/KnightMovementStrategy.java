package com.gp.chess.movement;

import static java.util.Arrays.asList;

import com.gp.chess.Column;
import com.gp.chess.Piece;
import com.gp.chess.Position;
import com.gp.chess.Row;
import com.gp.chess.Traversal;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KnightMovementStrategy extends MovementStrategy {

  public KnightMovementStrategy(Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    BiFunction<Position, Traversal<Row>, Position> fromRow = (p, r) -> new Position(p.getColumn(), r);
    BiFunction<Position, Traversal<Column>, Position> fromCol = (p, c) -> new Position(c, p.getRow());

    Traversal<Row> row = position.getRow();
    Traversal<Column> column = position.getColumn();

    Optional<Traversal<Row>> nextNextRow = tryGet2Step(position, p -> p.getRow().next(), fromRow);
    Optional<Position> move1 = tryToGetPosition(piece, nextNextRow, column.next());
    Optional<Position> move2 = tryToGetPosition(piece, nextNextRow, column.prev());

    Optional<Traversal<Column>> nextNextCol = tryGet2Step(position, p -> p.getColumn().next(), fromCol);
    Optional<Position> move3 = tryToGetPosition(piece, row.next(), nextNextCol);
    Optional<Position> move4 = tryToGetPosition(piece, row.prev(), nextNextCol);

    Optional<Traversal<Row>> prevPrevRow = tryGet2Step(position, p -> p.getRow().prev(), fromRow);
    Optional<Position> move5 = tryToGetPosition(piece, prevPrevRow, column.next());
    Optional<Position> move6 = tryToGetPosition(piece, prevPrevRow, column.prev());

    Optional<Traversal<Column>> prevPrevCol = tryGet2Step(position, p -> p.getColumn().prev(), fromCol);
    Optional<Position> move7 = tryToGetPosition(piece, row.next(), prevPrevCol);
    Optional<Position> move8 = tryToGetPosition(piece, row.prev(), prevPrevCol);

    return getAvailablePositions(asList(move1, move2, move3, move4, move5, move6, move7, move8));
  }

  private List<Position> getAvailablePositions(List<Optional<Position>> optionalPositions) {
    return optionalPositions.stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
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

  private Optional<Position> tryToGetPosition(Piece piece, Optional<Traversal<Row>> row, Optional<Traversal<Column>> col) {
    if (row.isPresent() && col.isPresent()) {
      Position candidate = new Position(col.get(), row.get());
      if (canOccupy.test(candidate) || canKill.test(piece, candidate)) {
        return Optional.of(candidate);
      }
    }
    return Optional.empty();
  }
}
