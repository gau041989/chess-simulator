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
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.PieceType.ROOK;
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
                from(D, FIVE, OCCUPY), from(D, SIX, OCCUPY), from(D, SEVEN, OCCUPY), from(D, EIGHT, OCCUPY),
                from(D, THREE, OCCUPY), from(D, TWO, OCCUPY), from(D, ONE, OCCUPY),
                from(C, FOUR, OCCUPY), from(B, FOUR, OCCUPY), from(A, FOUR, OCCUPY),
                from(E, FOUR, OCCUPY), from(F, FOUR, OCCUPY), from(G, FOUR, OCCUPY), from(H, FOUR, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                from(D, FIVE, OCCUPY), from(D, SIX, KILL),
                from(D, THREE, OCCUPY), from(D, TWO, KILL),
                from(C, FOUR, OCCUPY), from(B, FOUR, KILL),
                from(E, FOUR, OCCUPY), from(F, FOUR, OCCUPY), from(G, FOUR, KILL)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FOUR),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                from(D, FIVE, OCCUPY),
                from(D, THREE, OCCUPY),
                from(C, FOUR, OCCUPY),
                from(E, FOUR, OCCUPY), from(F, FOUR, OCCUPY)
            )
        )
    );
  }

  @ParameterizedTest(name = "Rook on {0}")
  @MethodSource("getData")
  @DisplayName("Rook movement strategy")
  public void givenARookPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<BoardAction> expectedMoves) {
    RookMovementStrategy strategy = new RookMovementStrategy(canOccupy, canKill);

    List<BoardAction> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, ROOK), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }

}