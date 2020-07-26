package com.gp.chess.movement;

import com.gp.chess.Piece;
import com.gp.chess.Position;
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
