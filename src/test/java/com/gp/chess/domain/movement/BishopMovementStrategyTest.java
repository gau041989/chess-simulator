package com.gp.chess.domain.movement;

import static com.gp.chess.domain.action.ActionType.KILL;
import static com.gp.chess.domain.action.ActionType.OCCUPY;
import static com.gp.chess.domain.action.BoardAction.from;
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

import com.gp.chess.domain.action.BoardAction;
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
                from(E, FIVE, OCCUPY), from(F, SIX, OCCUPY), from(G, SEVEN, OCCUPY), from(H, EIGHT, OCCUPY),
                from(C, THREE, OCCUPY), from(B, TWO, OCCUPY), from(A, ONE, OCCUPY),
                from(C, FIVE, OCCUPY), from(B, SIX, OCCUPY), from(A, SEVEN, OCCUPY),
                from(E, THREE, OCCUPY), from(F, TWO, OCCUPY), from(G, ONE, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                from(E, FIVE, OCCUPY), from(F, SIX, OCCUPY), from(G, SEVEN, KILL),
                from(C, THREE, OCCUPY), from(B, TWO, KILL),
                from(C, FIVE, OCCUPY), from(B, SIX, KILL),
                from(E, THREE, OCCUPY), from(F, TWO, KILL)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                from(E, FIVE, OCCUPY), from(F, SIX, OCCUPY),
                from(C, THREE, OCCUPY),
                from(C, FIVE, OCCUPY),
                from(E, THREE, OCCUPY)
            )
        )
    );
  }

  @ParameterizedTest(name = "Bishop on {0}")
  @MethodSource("getData")
  @DisplayName("Bishop movement strategy")
  public void givenABishopPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<BoardAction> expectedMoves) {
    BishopMovementStrategy strategy = new BishopMovementStrategy(canOccupy, canKill);

    List<BoardAction> possibleMoves = strategy.getPossibleMoves(new Piece(WHITE, BISHOP), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}