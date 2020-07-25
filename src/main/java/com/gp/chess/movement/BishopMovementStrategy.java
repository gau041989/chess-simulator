package com.gp.chess.movement;

import com.gp.chess.Column;
import com.gp.chess.Piece;
import com.gp.chess.Position;
import com.gp.chess.Row;
import com.gp.chess.Traversal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class BishopMovementStrategy extends MovementStrategy {

  public BishopMovementStrategy(Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    List<Position> rightUpperHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().next(),
        p -> p.getColumn().next(), new ArrayList<>());
    List<Position> rightLowerHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().prev(),
        p -> p.getColumn().prev(), new ArrayList<>());
    List<Position> leftUpperHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().next(),
        p -> p.getColumn().prev(), new ArrayList<>());
    List<Position> leftLowerHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().prev(),
        p -> p.getColumn().next(), new ArrayList<>());

    return new ArrayList<>(){{
      addAll(rightUpperHalfDiagonal);
      addAll(rightLowerHalfDiagonal);
      addAll(leftUpperHalfDiagonal);
      addAll(leftLowerHalfDiagonal);
    }};
  }

  private List<Position> getAvailableMoves(Piece piece, Position position,
      Function<Position, Optional<Traversal<Row>>> rowFn,
      Function<Position, Optional<Traversal<Column>>> colFn,
      List<Position> intermediateMoves) {
    Optional<Traversal<Row>> optionalRow = rowFn.apply(position);
    Optional<Traversal<Column>> optionalCol = colFn.apply(position);

    if(optionalRow.isPresent() && optionalCol.isPresent()) {
      Position candidate = new Position(optionalCol.get(), optionalRow.get());
      if (canOccupy.test(candidate)) {
        List<Position> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
        return getAvailableMoves(piece, candidate, rowFn, colFn, movesTillNow);
      } else if (canKill.test(piece, candidate)) {
        return new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
      }
    }
    return intermediateMoves;
  }
}
