package com.gp.chess.domain.movement;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Traversal;
import com.gp.chess.domain.character.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class RookMovementStrategy extends MovementStrategy {

  public RookMovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    List<Position> topMoves = getAvailableMoves(piece, position, p -> p.getRow().next(), fromRow, new ArrayList<>());
    List<Position> bottomMoves = getAvailableMoves(piece, position, p -> p.getRow().prev(), fromRow, new ArrayList<>());
    List<Position> leftMoves = getAvailableMoves(piece, position, p -> p.getColumn().prev(), fromCol,  new ArrayList<>());
    List<Position> rightMoves = getAvailableMoves(piece, position, p -> p.getColumn().next(), fromCol, new ArrayList<>());

    return new ArrayList<>() {{
      addAll(topMoves);
      addAll(bottomMoves);
      addAll(leftMoves);
      addAll(rightMoves);
    }};
  }

  private <T extends Traversal<T>> List<Position> getAvailableMoves(Piece piece, Position position,
      Function<Position, Optional<Traversal<T>>> traversalFn,
      BiFunction<Position, Traversal<T>, Position> fromTraversal,
      List<Position> intermediateMoves) {
    Optional<Traversal<T>> optionalTraversal = traversalFn.apply(position);
    if (optionalTraversal.isPresent()) {
      Position candidate = fromTraversal.apply(position, optionalTraversal.get());
      if (canOccupy.test(candidate)) {
        List<Position> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(candidate);
        }};
        return getAvailableMoves(piece, candidate, traversalFn, fromTraversal, movesTillNow);
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
