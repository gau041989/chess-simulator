package com.gp.chess.domain.movement;

import static com.gp.chess.domain.action.ActionType.KILL;
import static com.gp.chess.domain.action.ActionType.OCCUPY;
import static com.gp.chess.domain.action.BoardAction.from;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Column.F;
import static com.gp.chess.domain.cell.Column.G;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.ONE;
import static com.gp.chess.domain.cell.Row.THREE;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.PieceType.KNIGHT;
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
                from(F, FIVE, OCCUPY), from(G, FOUR, OCCUPY), from(G, TWO, OCCUPY),
                from(F, ONE, OCCUPY), from(D, ONE, OCCUPY), from(C, TWO, OCCUPY),
                from(C, FOUR, OCCUPY), from(D, FIVE, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(E, THREE),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                from(F, FIVE, KILL), from(G, FOUR, KILL), from(G, TWO, KILL),
                from(F, ONE, KILL), from(D, ONE, OCCUPY), from(C, TWO, OCCUPY),
                from(C, FOUR, OCCUPY), from(D, FIVE, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(E, THREE),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                from(D, ONE, OCCUPY), from(C, TWO, OCCUPY),
                from(C, FOUR, OCCUPY), from(D, FIVE, OCCUPY)
            )
        )
    );
  }

  @ParameterizedTest(name = "Knight on {0}")
  @MethodSource("getData")
  @DisplayName("Knight movement strategy")
  public void givenAKnightPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<BoardAction> expectedMoves) {
    KnightMovementStrategy strategy = new KnightMovementStrategy(canOccupy, canKill);

    List<BoardAction> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, KNIGHT), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}