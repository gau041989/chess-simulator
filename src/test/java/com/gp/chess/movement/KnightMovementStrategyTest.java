package com.gp.chess.movement;

import static com.gp.chess.Color.BLACK;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.Column.F;
import static com.gp.chess.Column.G;
import static com.gp.chess.PieceType.KNIGHT;
import static com.gp.chess.Row.FIVE;
import static com.gp.chess.Row.FOUR;
import static com.gp.chess.Row.ONE;
import static com.gp.chess.Row.THREE;
import static com.gp.chess.Row.TWO;
import static com.gp.chess.movement.Mocks.canKillFn;
import static com.gp.chess.movement.Mocks.canOccupyFn;
import static com.gp.chess.movement.Mocks.canOccupyPositionsFn;
import static com.gp.chess.movement.Mocks.cantKillPositionsFn;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.Piece;
import com.gp.chess.Position;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KnightMovementStrategyTest {

  private static Stream<Arguments> getData() {
    List<Position> occupiedCells = asList(new Position(F, FIVE), new Position(G, FOUR), new Position(G, TWO),
        new Position(F, ONE));
    return Stream.of(
        Arguments.of(
            "Empty Board",
            new Position(E, THREE),
            canOccupyFn.apply(true),
            canKillFn.apply(true),
            asList(
                new Position(F, FIVE), new Position(G, FOUR), new Position(G, TWO),
                new Position(F, ONE), new Position(D, ONE), new Position(C, TWO),
                new Position(C, FOUR), new Position(D, FIVE)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(E, THREE),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                new Position(F, FIVE), new Position(G, FOUR), new Position(G, TWO),
                new Position(F, ONE), new Position(D, ONE), new Position(C, TWO),
                new Position(C, FOUR), new Position(D, FIVE)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(E, THREE),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                new Position(D, ONE), new Position(C, TWO),
                new Position(C, FOUR), new Position(D, FIVE)
            )
        )
    );
  }

  @ParameterizedTest(name = "Knight on {0}")
  @MethodSource("getData")
  @DisplayName("Knight movement strategy")
  public void givenAKnightPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<Position> expectedMoves) {
    KnightMovementStrategy strategy = new KnightMovementStrategy(canOccupy, canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, KNIGHT), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}