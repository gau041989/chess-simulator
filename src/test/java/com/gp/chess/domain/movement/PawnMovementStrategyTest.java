package com.gp.chess.domain.movement;

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
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Color;
import com.gp.chess.domain.character.Piece;
import java.util.List;
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
            true,
            true,
            asList(new Position(D, THREE), new Position(D, FOUR), new Position(C, THREE), new Position(E, THREE))
        ),
        Arguments.of(
            "Filled Board -> Start of journey",
            WHITE,
            new Position(D, TWO),
            false,
            true,
            asList(new Position(C, THREE), new Position(E, THREE))
        ),
        Arguments.of(
            "Empty Board -> Start of journey -> Friends around",
            WHITE,
            new Position(D, TWO),
            true,
            false,
            asList(new Position(D, THREE), new Position(D, FOUR))
        ),
        Arguments.of(
            "Filled Board -> Start of journey -> Friends around",
            WHITE,
            new Position(D, TWO),
            false,
            false,
            emptyList()
        ),
        Arguments.of(
            "Empty Board -> Middle of journey",
            WHITE,
            new Position(D, THREE),
            true,
            true,
            asList(new Position(D, FOUR), new Position(C, FOUR), new Position(E, FOUR))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey",
            WHITE,
            new Position(D, THREE),
            false,
            true,
            asList(new Position(C, FOUR), new Position(E, FOUR))
        ),
        Arguments.of(
            "Empty Board -> Middle of journey -> Friends around",
            WHITE,
            new Position(D, THREE),
            true,
            false,
            singletonList(new Position(D, FOUR))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey -> Friends around",
            WHITE,
            new Position(D, THREE),
            false,
            false,
            emptyList()
        ),
        // Black pawns
        Arguments.of(
            "Empty Board -> Start of journey",
            BLACK,
            new Position(D, SEVEN),
            true,
            true,
            asList(new Position(D, SIX), new Position(C, SIX), new Position(E, SIX), new Position(D, FIVE))
        ),
        Arguments.of(
            "Filled Board -> Start of journey",
            BLACK,
            new Position(D, SEVEN),
            false,
            true,
            asList(new Position(C, SIX), new Position(E, SIX))
        ),
        Arguments.of(
            "Empty Board -> Start of journey -> Friends around",
            BLACK,
            new Position(D, SEVEN),
            true,
            false,
            asList(new Position(D, SIX), new Position(D, FIVE))
        ),
        Arguments.of(
            "Filled Board -> Start of journey -> Friends around",
            BLACK,
            new Position(D, SEVEN),
            false,
            false,
            emptyList()
        ),
        Arguments.of(
            "Empty Board -> Middle of journey",
            BLACK,
            new Position(D, SIX),
            true,
            true,
            asList(new Position(D, FIVE), new Position(C, FIVE), new Position(E, FIVE))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey",
            BLACK,
            new Position(D, SIX),
            false,
            true,
            asList(new Position(C, FIVE), new Position(E, FIVE))
        ),
        Arguments.of(
            "Empty Board -> Middle of journey -> Friends around",
            BLACK,
            new Position(D, SIX),
            true,
            false,
            singletonList(new Position(D, FIVE))
        ),
        Arguments.of(
            "Filled Board -> Middle of journey -> Friends around",
            BLACK,
            new Position(D, SIX),
            false,
            false,
            emptyList()
        )
    );
  }

  @ParameterizedTest(name = "{1} pawn on {0}")
  @MethodSource("getData")
  @DisplayName("Pawn movement strategy")
  public void givenAPawnPosition_shouldComputePossibleMoves(String scenario, Color color, Position position, boolean canOccupy,
      boolean canKill, List<Position> expectedMoves) {
    PawnMovementStrategy strategy = new PawnMovementStrategy(c -> canOccupy, (a, b) -> canKill);

    List<Position> possibleMoves = strategy.getPossibleMoves(new Piece(color, PAWN), position);

    assertThat(possibleMoves.size()).isEqualTo(expectedMoves.size());
    expectedMoves.forEach(move -> assertThat(possibleMoves.contains(move)).isTrue());
  }

}