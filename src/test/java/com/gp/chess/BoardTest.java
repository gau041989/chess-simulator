package com.gp.chess;

import static com.gp.chess.Color.BLACK;
import static com.gp.chess.Color.WHITE;
import static com.gp.chess.Column.B;
import static com.gp.chess.Column.C;
import static com.gp.chess.Column.D;
import static com.gp.chess.Column.E;
import static com.gp.chess.Column.F;
import static com.gp.chess.Column.G;
import static com.gp.chess.PieceType.BISHOP;
import static com.gp.chess.PieceType.PAWN;
import static com.gp.chess.PieceType.QUEEN;
import static com.gp.chess.PieceType.ROOK;
import static com.gp.chess.Row.FIVE;
import static com.gp.chess.Row.FOUR;
import static com.gp.chess.Row.SIX;
import static com.gp.chess.Row.THREE;
import static com.gp.chess.Row.TWO;
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

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForRook() {
    Position initialPosition = new Position(D, FOUR);
    Piece rook = new Piece(WHITE, ROOK);
    Board board = new BoardBuilder()
        .withPiece(rook, initialPosition)
        .withPiece(new Piece(BLACK, ROOK), new Position(D, SIX))
        .withPiece(new Piece(BLACK, BISHOP), new Position(B, FOUR))
        .withPiece(new Piece(WHITE, PAWN), new Position(D, TWO))
        .withPiece(new Piece(WHITE, QUEEN), new Position(G, FOUR))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(rook, initialPosition);

    assertThat(possibleMoves.size()).isEqualTo(7);
    assertThat(possibleMoves.contains(new Position(D, FIVE)));
    assertThat(possibleMoves.contains(new Position(D, SIX)));
    assertThat(possibleMoves.contains(new Position(C, FOUR)));
    assertThat(possibleMoves.contains(new Position(B, FOUR)));
    assertThat(possibleMoves.contains(new Position(E, FOUR)));
    assertThat(possibleMoves.contains(new Position(F, FOUR)));
    assertThat(possibleMoves.contains(new Position(D, THREE)));
  }
}