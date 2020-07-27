package com.gp.chess.domain.movement;

import static com.gp.chess.domain.cell.Row.SEVEN;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.Color.WHITE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import com.gp.chess.domain.action.ActionType;
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

public class PawnMovementStrategy extends MovementStrategy {

  public PawnMovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<BoardAction> getPossibleMoves(Piece piece, Position position) {
    if (piece.getColor().equals(WHITE)) {
      return getMovesForWhitePawn(piece, position);
    } else if (piece.getColor().equals(BLACK)) {
      return getMovesForBlackPawn(piece, position);
    }

    return emptyList();
  }

  private List<BoardAction> getMovesForWhitePawn(Piece piece, Position position) {
    Optional<BoardAction> move1 = tryToOccupy(position, p -> p.getRow().next(), fromRow);
    Optional<BoardAction> move2 = tryToKill(piece, position, p -> p.getRow().next(), p -> p.getColumn().prev());
    Optional<BoardAction> move3 = tryToKill(piece, position, p -> p.getRow().next(), p -> p.getColumn().next());
    Optional<BoardAction> move4 = tryInitialMove(position, TWO, Traversal::next);

    return getAvailableMoves(asList(move1, move2, move3, move4));
  }

  private List<BoardAction> getMovesForBlackPawn(Piece piece, Position position) {
    Optional<BoardAction> move1 = tryToOccupy(position, p -> p.getRow().prev(), fromRow);
    Optional<BoardAction> move2 = tryToKill(piece, position, p -> p.getRow().prev(), p -> p.getColumn().next());
    Optional<BoardAction> move3 = tryToKill(piece, position, p -> p.getRow().prev(), p -> p.getColumn().prev());
    Optional<BoardAction> move4 = tryInitialMove(position, SEVEN, Traversal::prev);

    return getAvailableMoves(asList(move1, move2, move3, move4));
  }

  private <T extends Traversal<T>> Optional<BoardAction> tryToOccupy(Position position,
      Function<Position, Optional<Traversal<T>>> traversalFn,
      BiFunction<Position, Traversal<T>, Position> fromTraversal) {
    return traversalFn.apply(position).flatMap(traversal -> {
      Position candidate = fromTraversal.apply(position, traversal);
      if (canOccupy.test(candidate)) {
        return Optional.of(new BoardAction(ActionType.OCCUPY, candidate));
      }
      return Optional.empty();
    });
  }

  private Optional<BoardAction> tryToKill(Piece piece, Position position,
      Function<Position, Optional<Traversal<Row>>> rowFn,
      Function<Position, Optional<Traversal<Column>>> colFn) {
    Optional<Traversal<Row>> row = rowFn.apply(position);
    Optional<Traversal<Column>> col = colFn.apply(position);
    if(row.isPresent() && col.isPresent()) {
      Position candidate = new Position(col.get(), row.get());
      if (canKill.test(piece, candidate)) {
        return Optional.of(new BoardAction(ActionType.KILL, candidate));
      }
    }
    return Optional.empty();
  }

  private Optional<BoardAction> tryInitialMove(Position position, Row initialRow,
      Function<Traversal<Row>, Optional<Traversal<Row>>> rowFn) {
    Optional<Traversal<Row>> firstRow = rowFn.apply(position.getRow());
    return firstRow.flatMap(r -> {
      if (position.getRow().equals(initialRow) && canOccupy.test(new Position(position.getColumn(), r))) {
        Position candidate = new Position(position.getColumn(), rowFn.apply(r).get());
        if (canOccupy.test(candidate)) {
          return Optional.of(new BoardAction(ActionType.OCCUPY, candidate));
        }
      }
      return Optional.empty();
    });
  }

}
