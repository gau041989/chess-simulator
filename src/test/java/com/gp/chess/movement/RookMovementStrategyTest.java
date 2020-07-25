package com.gp.chess.movement;

import static com.gp.chess.Color.BLACK;
import static com.gp.chess.Column.A;
import static com.gp.chess.Column.B;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.Column.F;
import static com.gp.chess.Column.G;
import static com.gp.chess.Column.H;
import static com.gp.chess.PieceType.PAWN;
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

class RookMovementStrategyTest {

  private static Stream<Arguments> getData() {
    List<Position> occupiedCells = asList(new Position(D, SIX), new Position(D, TWO), new Position(G, FOUR),
        new Position(B, FOUR));
    return Stream.of(
        Arguments.of(
            "Empty Board",
            new Position(D, FOUR),
            canOccupyFn.apply(true),
            canKillFn.apply(true),
            asList(
                new Position(D, FIVE), new Position(D, SIX), new Position(D, SEVEN), new Position(D, EIGHT),
                new Position(D, THREE), new Position(D, TWO), new Position(D, ONE),
                new Position(C, FOUR), new Position(B, FOUR), new Position(A, FOUR),
                new Position(E, FOUR), new Position(F, FOUR), new Position(G, FOUR), new Position(H, FOUR)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                new Position(D, FIVE), new Position(D, SIX),
                new Position(D, THREE), new Position(D, TWO),
                new Position(C, FOUR), new Position(B, FOUR),
                new Position(E, FOUR), new Position(F, FOUR), new Position(G, FOUR)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                new Position(D, FIVE),
                new Position(D, THREE),
                new Position(C, FOUR),
                new Position(E, FOUR), new Position(F, FOUR)
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


  @ParameterizedTest(name = "Rook on {0}")
  @MethodSource("getData")
  @DisplayName("Rook movement strategy")
  public void givenAPawnPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<Position> expectedMoves) {
    RookMovementStrategy strategy = new RookMovementStrategy(canOccupy, canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, PAWN), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)));
  }

}