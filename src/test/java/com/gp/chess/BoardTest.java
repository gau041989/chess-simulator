package com.gp.chess;

import static com.gp.chess.Color.BLACK;
import static com.gp.chess.Color.WHITE;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.PieceType.BISHOP;
import static com.gp.chess.PieceType.PAWN;
import static com.gp.chess.PieceType.ROOK;
import static com.gp.chess.Row.FOUR;
import static com.gp.chess.Row.THREE;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.Board.BoardBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForPawn() {
    Position pawnInitialPosition = new Position(D, Row.TWO);
    Piece pawn = new Piece(WHITE, PAWN);
    Board board = new BoardBuilder()
        .withPiece(pawn, pawnInitialPosition)
        .withPiece(new Piece(BLACK, ROOK), new Position(C, THREE))
        .withPiece(new Piece(BLACK, BISHOP), new Position(E, THREE))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(pawn, pawnInitialPosition);

    assertThat(possibleMoves.size()).isEqualTo(4);
    assertThat(possibleMoves.contains(new Position(D, THREE)));
    assertThat(possibleMoves.contains(new Position(D, FOUR)));
    assertThat(possibleMoves.contains(new Position(C, THREE)));
    assertThat(possibleMoves.contains(new Position(E, THREE)));
  }
}