package com.gp.chess.domain.movement;

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