package com.gp.chess.domain.movement;

import static com.gp.chess.domain.action.ActionType.KILL;
import static com.gp.chess.domain.action.ActionType.OCCUPY;
import static com.gp.chess.domain.action.BoardAction.from;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.SEVEN;
import static com.gp.chess.domain.cell.Row.SIX;
import static com.gp.chess.domain.cell.Row.THREE;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.Color.WHITE;
import static com.gp.chess.domain.character.PieceType.PAWN;
import static com.gp.chess.domain.movement.Mocks.canKillFn;
import static com.gp.chess.domain.movement.Mocks.canOccupyFn;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Color;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnMovementStrategyTest {

  private static Stream<Arguments> getData() {
    return Stream.of(
        Arguments.of(
            "Empty Board -> Start of journey",
            WHITE,
            new Position(D, TWO),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            asList(from(D, THREE, OCCUPY), from(D, FOUR, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Start of journey",
            WHITE,
            new Position(D, TWO),
            canOccupyFn.apply(false),
            canKillFn.apply(true),
            asList(from(C, THREE, KILL), from(E, THREE, KILL))
        ),
        Arguments.of(
            "Empty Board -> Start of journey -> Friends around",
            WHITE,
            new Position(D, TWO),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            asList(from(D, THREE, OCCUPY), from(D, FOUR, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Start of journey -> Friends around",
            WHITE,
            new Position(D, TWO),
            canOccupyFn.apply(false),
            canKillFn.apply(false),
            emptyList()
        ),
        Arguments.of(
            "Empty Board -> Middle of journey",
            WHITE,
            new Position(D, THREE),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            singletonList(from(D, FOUR, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey",
            WHITE,
            new Position(D, THREE),
            canOccupyFn.apply(false),
            canKillFn.apply(true),
            asList(from(C, FOUR, KILL), from(E, FOUR, KILL))
        ),
        Arguments.of(
            "Empty Board -> Middle of journey -> Friends around",
            WHITE,
            new Position(D, THREE),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            singletonList(from(D, FOUR, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey -> Friends around",
            WHITE,
            new Position(D, THREE),
            canOccupyFn.apply(false),
            canKillFn.apply(false),
            emptyList()
        ),
        // Black pawns
        Arguments.of(
            "Empty Board -> Start of journey",
            BLACK,
            new Position(D, SEVEN),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            asList(from(D, SIX, OCCUPY), from(D, FIVE, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Start of journey",
            BLACK,
            new Position(D, SEVEN),
            canOccupyFn.apply(false),
            canKillFn.apply(true),
            asList(from(C, SIX, KILL), from(E, SIX, KILL))
        ),
        Arguments.of(
            "Empty Board -> Start of journey -> Friends around",
            BLACK,
            new Position(D, SEVEN),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            asList(from(D, SIX, OCCUPY), from(D, FIVE, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Start of journey -> Friends around",
            BLACK,
            new Position(D, SEVEN),
            canOccupyFn.apply(false),
            canKillFn.apply(false),
            emptyList()
        ),
        Arguments.of(
            "Empty Board -> Middle of journey",
            BLACK,
            new Position(D, SIX),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            asList(from(D, FIVE, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey",
            BLACK,
            new Position(D, SIX),
            canOccupyFn.apply(false),
            canKillFn.apply(true),
            asList(from(C, FIVE, KILL), from(E, FIVE, KILL))
        ),
        Arguments.of(
            "Empty Board -> Middle of journey -> Friends around",
            BLACK,
            new Position(D, SIX),
            canOccupyFn.apply(true),
            canKillFn.apply(false),
            singletonList(from(D, FIVE, OCCUPY))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey -> Friends around",
            BLACK,
            new Position(D, SIX),
            canOccupyFn.apply(false),
            canKillFn.apply(false),
            emptyList()
        )
    );
  }

  @ParameterizedTest(name = "{1} pawn on {0}")
  @MethodSource("getData")
  @DisplayName("Pawn movement strategy")
  public void givenAPawnPosition_shouldComputePossibleMoves(String scenario, Color color, Position position, Predicate<Position> canOccupy,
      BiPredicate<Piece, Position> canKill, List<BoardAction> expectedMoves) {
    PawnMovementStrategy strategy = new PawnMovementStrategy(canOccupy, canKill);

    List<BoardAction> possibleMoves = strategy.getPossibleMoves(new Piece(color, PAWN), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }

}