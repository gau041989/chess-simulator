package com.gp.chess.movement;

import static com.gp.chess.Color.WHITE;
import static com.gp.chess.Column.A;
import static com.gp.chess.Column.B;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.Column.F;
import static com.gp.chess.Column.G;
import static com.gp.chess.Column.H;
import static com.gp.chess.PieceType.BISHOP;
import static com.gp.chess.Row.EIGHT;
import static com.gp.chess.Row.FIVE;
import static com.gp.chess.Row.FOUR;
import static com.gp.chess.Row.ONE;
import static com.gp.chess.Row.SEVEN;
import static com.gp.chess.Row.SIX;
import static com.gp.chess.Row.THREE;
import static com.gp.chess.Row.TWO;
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

class BishopMovementStrategyTest {

  private static Stream<Arguments> getData() {
    List<Position> occupiedCells = asList(new Position(G, SEVEN), new Position(B, TWO), new Position(B, SIX),
        new Position(F, TWO));

    return Stream.of(
        Arguments.of(
            "Empty Board",
            new Position(D, FOUR),
            canOccupyFn.apply(true),
            canKillFn.apply(true),
            asList(
                new Position(E, FIVE), new Position(F, SIX), new Position(G, SEVEN), new Position(H, EIGHT),
                new Position(C, THREE), new Position(B, TWO), new Position(A, ONE),
                new Position(C, FIVE), new Position(B, SIX), new Position(A, SEVEN),
                new Position(E, THREE), new Position(F, TWO), new Position(G, ONE)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                new Position(E, FIVE), new Position(F, SIX), new Position(G, SEVEN),
                new Position(C, THREE), new Position(B, TWO),
                new Position(C, FIVE), new Position(B, SIX),
                new Position(E, THREE), new Position(F, TWO)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                new Position(E, FIVE), new Position(F, SIX),
                new Position(C, THREE),
                new Position(C, FIVE),
                new Position(E, THREE)
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

  @ParameterizedTest(name = "Bishop on {0}")
  @MethodSource("getData")
  @DisplayName("Bishop movement strategy")
  public void givenABishopPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<Position> expectedMoves) {
    BishopMovementStrategy strategy = new BishopMovementStrategy(canOccupy, canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(WHITE, BISHOP), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)));
  }
}