package com.gp.chess.domain.movement;

import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.THREE;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.PieceType.QUEEN;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QueenMovementStrategyTest {

  @Test
  public void givenAQueenPosition_shouldComputePossibleMoves() {
    RookMovementStrategy mockRookStrategy = Mockito.mock(RookMovementStrategy.class);
    BishopMovementStrategy mockBishopStrategy = Mockito.mock(BishopMovementStrategy.class);
    Predicate<Position> mockCanOccupy = a -> true;
    BiPredicate<Piece, Position> mockCanKill = (a, b) -> true;

    Piece piece = new Piece(BLACK, QUEEN);
    Position position = new Position(D, FOUR);
    given(mockBishopStrategy.getPossibleMoves(piece, position))
        .willReturn(asList(new Position(E, FIVE), new Position(C, THREE)));
    given(mockRookStrategy.getPossibleMoves(piece, position))
        .willReturn(asList(new Position(C, FIVE), new Position(D, FOUR)));
    QueenMovementStrategy strategy = new QueenMovementStrategy(mockCanOccupy, mockCanKill, mockRookStrategy, mockBishopStrategy);

    List<Position> possibleMoves = strategy.getPossibleMoves(piece, position);

    assertThat(possibleMoves.size()).isEqualTo(4);
    assertThat(possibleMoves.contains(new Position(E, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, THREE))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, FOUR))).isTrue();
    verify(mockBishopStrategy, times(1)).getPossibleMoves(piece, position);
    verify(mockRookStrategy, times(1)).getPossibleMoves(piece, position);
  }

}