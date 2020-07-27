package com.gp.chess.domain.movement;

import com.gp.chess.domain.action.ActionType;
import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.cell.Traversal;
import com.gp.chess.domain.character.Piece;
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
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    List<BoardAction> rightUpperHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().next(),
        p -> p.getColumn().next(), new ArrayList<>());
    List<BoardAction> rightLowerHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().prev(),
        p -> p.getColumn().prev(), new ArrayList<>());
    List<BoardAction> leftUpperHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().next(),
        p -> p.getColumn().prev(), new ArrayList<>());
    List<BoardAction> leftLowerHalfDiagonal = getAvailableMoves(piece, position, p -> p.getRow().prev(),
        p -> p.getColumn().next(), new ArrayList<>());

    return new ArrayList<>(){{
      addAll(rightUpperHalfDiagonal);
      addAll(rightLowerHalfDiagonal);
      addAll(leftUpperHalfDiagonal);
      addAll(leftLowerHalfDiagonal);
    }};
  }

  private List<BoardAction> getAvailableMoves(Piece piece, Position position,
      Function<Position, Optional<Traversal<Row>>> rowFn,
      Function<Position, Optional<Traversal<Column>>> colFn,
      List<BoardAction> intermediateMoves) {
    Optional<Traversal<Row>> optionalRow = rowFn.apply(position);
    Optional<Traversal<Column>> optionalCol = colFn.apply(position);

    if(optionalRow.isPresent() && optionalCol.isPresent()) {
      Position candidate = new Position(optionalCol.get(), optionalRow.get());
      if (canOccupy.test(candidate)) {
        List<BoardAction> movesTillNow = new ArrayList<>() {{
          addAll(intermediateMoves);
          add(new BoardAction(ActionType.OCCUPY, candidate));
        }};
        return getAvailableMoves(piece, candidate, rowFn, colFn, movesTillNow);
      } else if (canKill.test(piece, candidate)) {
        return new ArrayList<>() {{
          addAll(intermediateMoves);
          add(new BoardAction(ActionType.KILL, candidate));
        }};
      }
    }
    return intermediateMoves;
  }
}
