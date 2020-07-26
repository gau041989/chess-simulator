package com.gp.chess.domain.movement;

import static com.gp.chess.domain.cell.Column.A;
import static com.gp.chess.domain.cell.Column.B;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Column.F;
import static com.gp.chess.domain.cell.Column.G;
import static com.gp.chess.domain.cell.Column.H;
import static com.gp.chess.domain.cell.Row.EIGHT;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.ONE;
import static com.gp.chess.domain.cell.Row.SEVEN;
import static com.gp.chess.domain.cell.Row.SIX;
import static com.gp.chess.domain.cell.Row.THREE;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.WHITE;
import static com.gp.chess.domain.character.PieceType.BISHOP;
import static com.gp.chess.domain.movement.Mocks.canKillFn;
import static com.gp.chess.domain.movement.Mocks.canOccupyFn;
import static com.gp.chess.domain.movement.Mocks.canOccupyPositionsFn;
import static com.gp.chess.domain.movement.Mocks.cantKillPositionsFn;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.function.BiPredicate;
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

  @ParameterizedTest(name = "Bishop on {0}")
  @MethodSource("getData")
  @DisplayName("Bishop movement strategy")
  public void givenABishopPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<Position> expectedMoves) {
    BishopMovementStrategy strategy = new BishopMovementStrategy(canOccupy, canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(WHITE, BISHOP), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}