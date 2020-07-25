package com.gp.chess.movement;

import com.gp.chess.Column;
import com.gp.chess.Piece;
import com.gp.chess.Position;
import com.gp.chess.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class RookMovementStrategy extends MovementStrategy {

  public RookMovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    List<Position> topMoves = getAvailableVerticalMoves(piece, position, p -> p.getRow().next(), new ArrayList<>());
    List<Position> bottomMoves = getAvailableVerticalMoves(piece, position, p -> p.getRow().prev(), new ArrayList<>());
    List<Position> leftMoves = getAvailableHorizontalMoves(piece, position, p -> p.getColumn().prev(), new ArrayList<>());
    List<Position> rightMoves = getAvailableHorizontalMoves(piece, position, p -> p.getColumn().next(), new ArrayList<>());

    return new ArrayList<>() {{
      addAll(topMoves);
      addAll(bottomMoves);
      addAll(leftMoves);
      addAll(rightMoves);
    }};
  }

  private List<Position> getAvailableVerticalMoves(Piece piece, Position position,
      Function<Position, Optional<Row>> rowFn, List<Position> intermediateMoves) {
    Optional<Row> optionalRow = rowFn.apply(position);
    if (optionalRow.isPresent()) {
      Position candidate = (new Position(position.getColumn(), optionalRow.get()));
      if (canOccupy.test(candidate)) {
        List<Position> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
        return getAvailableVerticalMoves(piece, candidate, rowFn, movesTillNow);
      } else if (canKill.test(piece, candidate)) {
        return new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
      }
      return intermediateMoves;
    }
    return intermediateMoves;
  }

  private List<Position> getAvailableHorizontalMoves(Piece piece, Position position,
      Function<Position, Optional<Column>> colFn, List<Position> intermediateMoves) {
    Optional<Column> optionalColumn = colFn.apply(position);
    if (optionalColumn.isPresent()) {
      Position candidate = (new Position(optionalColumn.get(), position.getRow()));
      if (canOccupy.test(candidate)) {
        List<Position> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
        return getAvailableHorizontalMoves(piece, candidate, colFn, movesTillNow);
      } else if (canKill.test(piece, candidate)) {
        return new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
      }
      return intermediateMoves;
    }
    return intermediateMoves;
  }
}
