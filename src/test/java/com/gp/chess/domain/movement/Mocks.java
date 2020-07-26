package com.gp.chess.domain.movement;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Mocks {
  public static Function<Boolean, Predicate<Position>> canOccupyFn = aBoolean -> position -> aBoolean;

  public static Function<Boolean, BiPredicate<Piece, Position>> canKillFn = aBoolean -> (piece, position) -> aBoolean;

  public static Function<List<Position>, Predicate<Position>> canOccupyPositionsFn =
      positions -> position -> !positions.contains(position);

  public static Function<List<Position>, BiPredicate<Piece, Position>> cantKillPositionsFn =
      positions -> (piece, position) -> !positions.contains(position);
}
