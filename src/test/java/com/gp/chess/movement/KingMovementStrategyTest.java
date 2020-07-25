package com.gp.chess.movement;

import static com.gp.chess.Color.BLACK;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.PieceType.KING;
import static com.gp.chess.Row.FIVE;
import static com.gp.chess.Row.FOUR;
import static com.gp.chess.Row.SIX;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.Piece;
import com.gp.chess.Position;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KingMovementStrategyTest {

  private static Stream<Arguments> getData() {
    List<Position> occupiedCells = asList(new Position(D, SIX), new Position(E, SIX), new Position(D, FOUR),
        new Position(E, FOUR));
    return Stream.of(
        Arguments.of(
            "Empty Board",
            new Position(D, FIVE),
            canOccupyFn.apply(true),
            canKillFn.apply(true),
            asList(
                new Position(D, SIX), new Position(E, SIX), new Position(E, FIVE),
                new Position(E, FOUR), new Position(D, FOUR), new Position(C, FOUR),
                new Position(C, FIVE), new Position(C, SIX)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FIVE),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                new Position(D, SIX), new Position(E, SIX), new Position(E, FIVE),
                new Position(E, FOUR), new Position(D, FOUR), new Position(C, FOUR),
                new Position(C, FIVE), new Position(C, SIX)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FIVE),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                new Position(E, FIVE),
                new Position(C, FOUR),
                new Position(C, FIVE), new Position(C, SIX)
            )
        )
    );
  }

  private static Function<Boolean, Predicate<Position>> canOccupyFn = aBoolean -> position -> aBoolean;

  private static Function<Boolean, BiPredicate<Piece, Position>> canKillFn = aBoolean -> (piece, position) -> aBoolean;

  private static Function<List<Position>, Predicate<Position>> canOccupyPositionsFn =
      positions -> position -> !positions.contains(position);

  private static Function<List<Position>, BiPredicate<Piece, Position>> cantKillPositionsFn =
      positions -> (piece, position) -> !positions.contains(position);

  @ParameterizedTest(name = "King on {0}")
  @MethodSource("getData")
  @DisplayName("King movement strategy")
  public void givenAKingPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<Position> expectedMoves) {
    KingMovementStrategy strategy = new KingMovementStrategy(canOccupy, canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, KING), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}