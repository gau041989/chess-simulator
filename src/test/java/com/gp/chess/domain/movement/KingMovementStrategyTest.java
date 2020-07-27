package com.gp.chess.domain.movement;

import static com.gp.chess.domain.action.ActionType.KILL;
import static com.gp.chess.domain.action.ActionType.OCCUPY;
import static com.gp.chess.domain.action.BoardAction.from;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.SIX;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.PieceType.KING;
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
                from(D, SIX, OCCUPY), from(E, SIX, OCCUPY), from(E, FIVE, OCCUPY),
                from(E, FOUR, OCCUPY), from(D, FOUR, OCCUPY), from(C, FOUR, OCCUPY),
                from(C, FIVE, OCCUPY), from(C, SIX, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with enemies to kill",
            new Position(D, FIVE),
            canOccupyPositionsFn.apply(occupiedCells),
            canKillFn.apply(true),
            asList(
                from(D, SIX, KILL), from(E, SIX, KILL), from(E, FIVE, OCCUPY),
                from(E, FOUR, KILL), from(D, FOUR, KILL), from(C, FOUR, OCCUPY),
                from(C, FIVE, OCCUPY), from(C, SIX, OCCUPY)
            )
        ),
        Arguments.of(
            "Filled Board with friends around",
            new Position(D, FIVE),
            canOccupyPositionsFn.apply(occupiedCells),
            cantKillPositionsFn.apply(occupiedCells),
            asList(
                from(E, FIVE, OCCUPY),
                from(C, FOUR, OCCUPY),
                from(C, FIVE, OCCUPY), from(C, SIX, OCCUPY)
            )
        )
    );
  }

  @ParameterizedTest(name = "King on {0}")
  @MethodSource("getData")
  @DisplayName("King movement strategy")
  public void givenAKingPosition_shouldComputePossibleMoves(String scenario, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<BoardAction> expectedMoves) {
    KingMovementStrategy strategy = new KingMovementStrategy(canOccupy, canKill);

    List<BoardAction> possibleMoves = strategy.getPossibleMoves(new Piece(BLACK, KING), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }
}