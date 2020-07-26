package com.gp.chess.domain.movement;

import static com.gp.chess.domain.cell.Row.SEVEN;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.Color.WHITE;
import static java.util.Collections.emptyList;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PawnMovementStrategy extends MovementStrategy {

  public PawnMovementStrategy(Predicate<Position> canOccupy, BiPredicate<Piece, Position> canKill) {
    super(canOccupy, canKill);
  }

  @Override
  public List<Position> getPossibleMoves(Piece piece, Position position) {
    if (piece.getColor().equals(WHITE)) {
      return getPositionsForWhitePawn(piece, position);
    } else if (piece.getColor().equals(BLACK)) {
      return getPositionsForBlackPawn(piece, position);
    }

    return emptyList();
  }

  private List<Position> getPositionsForWhitePawn(Piece piece, Position position) {
    List<Position> possibleMoves = new ArrayList<>();

    position.getRow().next().ifPresent(row -> {
      Position candidate = new Position(position.getColumn(), row);
      if (canOccupy.test(candidate)) {
        possibleMoves.add(candidate);
      }
    });

    position.getRow().next().ifPresent(row -> {
      position.getColumn().prev().ifPresent(column -> {
        Position candidate = new Position(column, row);
        if (canKill.test(piece, candidate)) {
          possibleMoves.add(candidate);
        }
      });
    });

    position.getRow().next().ifPresent(row -> {
      position.getColumn().next().ifPresent(column -> {
        Position candidate = new Position(column, row);
        if (canKill.test(piece, candidate)) {
          possibleMoves.add(candidate);
        }
      });
    });

    position.getRow().next().ifPresent(row -> {
      if (position.getRow().equals(TWO) && canOccupy.test(new Position(position.getColumn(), row))) {
        Position candidate = new Position(position.getColumn(), row.next().get());
        if (canOccupy.test(candidate)) {
          possibleMoves.add(candidate);
        }
      }
    });

    return possibleMoves;
  }

  private List<Position> getPositionsForBlackPawn(Piece piece, Position position) {
    List<Position> possibleMoves = new ArrayList<>();

    position.getRow().prev().ifPresent(row -> {
      Position candidate = new Position(position.getColumn(), row);
      if (canOccupy.test(candidate)) {
        possibleMoves.add(candidate);
      }
    });

    position.getRow().prev().ifPresent(row -> {
      position.getColumn().next().ifPresent(column -> {
        Position candidate = new Position(column, row);
        if (canKill.test(piece, candidate)) {
          possibleMoves.add(candidate);
        }
      });
    });

    position.getRow().prev().ifPresent(row -> {
      position.getColumn().prev().ifPresent(column -> {
        Position candidate = new Position(column, row);
        if (canKill.test(piece, candidate)) {
          possibleMoves.add(candidate);
        }
      });
    });

    position.getRow().prev().ifPresent(row -> {
      if (position.getRow().equals(SEVEN) && canOccupy.test(new Position(position.getColumn(), row))) {
        Position candidate = new Position(position.getColumn(), row.prev().get());
        if (canOccupy.test(candidate)) {
          possibleMoves.add(candidate);
        }
      }
    });

    return possibleMoves;
  }

}
