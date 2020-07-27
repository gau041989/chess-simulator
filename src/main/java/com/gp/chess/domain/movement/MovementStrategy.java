package com.gp.chess.domain.movement;

import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.cell.Traversal;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MovementStrategy {

  protected final Predicate<Position> canOccupy;
  protected final BiPredicate<Piece, Position> canKill;

  public MovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    this.canOccupy = canOccupy;
    this.canKill = canKill;
  }

  public abstract List<Position> getPossibleMoves(Piece piece, Position position);

  protected List<Position> getAvailablePositions(List<Optional<Position>> optionalPositions) {
    return optionalPositions.stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  protected BiFunction<Position, Traversal<Row>, Position> fromRow = (p, r) -> new Position(p.getColumn(), r);

  protected BiFunction<Position, Traversal<Column>, Position> fromCol = (p, c) -> new Position(c, p.getRow());

  protected Optional<Position> tryToGetPosition(Piece piece, Optional<Traversal<Row>> row, Optional<Traversal<Column>> col) {
    if (row.isPresent() && col.isPresent()) {
      Position candidate = new Position(col.get(), row.get());
      if (canOccupy.test(candidate) || canKill.test(piece, candidate)) {
        return Optional.of(candidate);
      }
    }
    return Optional.empty();
  }
}
