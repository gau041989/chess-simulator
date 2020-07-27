package com.gp.chess.domain.movement;

import com.gp.chess.domain.action.ActionType;
import com.gp.chess.domain.action.BoardAction;
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
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    List<BoardAction> topMoves = getAvailableMoves(piece, position, p -> p.getRow().next(), fromRow, new ArrayList<>());
    List<BoardAction> bottomMoves = getAvailableMoves(piece, position, p -> p.getRow().prev(), fromRow, new ArrayList<>());
    List<BoardAction> leftMoves = getAvailableMoves(piece, position, p -> p.getColumn().prev(), fromCol,  new ArrayList<>());
    List<BoardAction> rightMoves = getAvailableMoves(piece, position, p -> p.getColumn().next(), fromCol, new ArrayList<>());

    return new ArrayList<>() {{
      addAll(topMoves);
      addAll(bottomMoves);
      addAll(leftMoves);
      addAll(rightMoves);
    }};
  }

  private <T extends Traversal<T>> List<BoardAction> getAvailableMoves(Piece piece, Position position,
      Function<Position, Optional<Traversal<T>>> traversalFn,
      BiFunction<Position, Traversal<T>, Position> fromTraversal,
      List<BoardAction> intermediateMoves) {
    Optional<Traversal<T>> optionalTraversal = traversalFn.apply(position);
    if (optionalTraversal.isPresent()) {
      Position candidate = fromTraversal.apply(position, optionalTraversal.get());
      if (canOccupy.test(candidate)) {
        List<BoardAction> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(new BoardAction(ActionType.OCCUPY, candidate));
        }};
        return getAvailableMoves(piece, candidate, traversalFn, fromTraversal, movesTillNow);
      } else if (canKill.test(piece, candidate)) {
        return new ArrayList<>() {{
          addAll(intermediateMoves);
          add(new BoardAction(ActionType.KILL, candidate));
        }};
      }
      return intermediateMoves;
    }
    return intermediateMoves;
  }
}
