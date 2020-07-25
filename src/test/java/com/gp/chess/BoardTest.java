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
import static com.gp.chess.PieceType.KNIGHT;
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
    assertThat(possibleMoves.contains(new Position(D, THREE))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, THREE))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, THREE))).isTrue();
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
    assertThat(possibleMoves.contains(new Position(D, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(B, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(F, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, THREE))).isTrue();
  }

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForBishop() {
    Position initialPosition = new Position(D, FOUR);
    Piece bishop = new Piece(WHITE, BISHOP);
    Board board = new BoardBuilder()
        .withPiece(bishop, initialPosition)
        .withPiece(new Piece(BLACK, ROOK), new Position(F, SIX))
        .withPiece(new Piece(BLACK, BISHOP), new Position(B, SIX))
        .withPiece(new Piece(WHITE, PAWN), new Position(F, TWO))
        .withPiece(new Piece(WHITE, QUEEN), new Position(B, TWO))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(bishop, initialPosition);

    assertThat(possibleMoves.size()).isEqualTo(6);
    assertThat(possibleMoves.contains(new Position(E, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(F, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(B, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, THREE))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, THREE))).isTrue();
  }

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForQueen() {
    Position initialPosition = new Position(D, FOUR);
    Piece queen = new Piece(WHITE, QUEEN);
    Board board = new BoardBuilder()
        .withPiece(queen, initialPosition)
        //rook
        .withPiece(new Piece(BLACK, ROOK), new Position(D, SIX))
        .withPiece(new Piece(BLACK, BISHOP), new Position(B, FOUR))
        .withPiece(new Piece(WHITE, PAWN), new Position(D, TWO))
        .withPiece(new Piece(WHITE, QUEEN), new Position(G, FOUR))
        //bishop
        .withPiece(new Piece(BLACK, ROOK), new Position(F, SIX))
        .withPiece(new Piece(BLACK, BISHOP), new Position(B, SIX))
        .withPiece(new Piece(WHITE, PAWN), new Position(F, TWO))
        .withPiece(new Piece(WHITE, KNIGHT), new Position(B, TWO))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(queen, initialPosition);

    assertThat(possibleMoves.size()).isEqualTo(13);
    //rook
    assertThat(possibleMoves.contains(new Position(D, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(B, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(F, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, THREE))).isTrue();
    //bishop
    assertThat(possibleMoves.contains(new Position(E, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(F, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(B, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, THREE))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, THREE))).isTrue();
  }
}