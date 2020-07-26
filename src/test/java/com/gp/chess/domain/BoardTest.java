package com.gp.chess.domain;

import static com.gp.chess.domain.cell.Column.B;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Column.F;
import static com.gp.chess.domain.cell.Column.G;
import static com.gp.chess.domain.cell.Row.FIVE;
import static com.gp.chess.domain.cell.Row.FOUR;
import static com.gp.chess.domain.cell.Row.ONE;
import static com.gp.chess.domain.cell.Row.SIX;
import static com.gp.chess.domain.cell.Row.THREE;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.Color.WHITE;
import static com.gp.chess.domain.character.PieceType.BISHOP;
import static com.gp.chess.domain.character.PieceType.KING;
import static com.gp.chess.domain.character.PieceType.KNIGHT;
import static com.gp.chess.domain.character.PieceType.PAWN;
import static com.gp.chess.domain.character.PieceType.QUEEN;
import static com.gp.chess.domain.character.PieceType.ROOK;
import static org.assertj.core.api.Assertions.assertThat;

import com.gp.chess.domain.Board.BoardBuilder;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.character.Piece;
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

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForKing() {
    Position initialPosition = new Position(D, FIVE);
    Piece king = new Piece(WHITE, KING);
    Board board = new BoardBuilder()
        .withPiece(king, initialPosition)
        .withPiece(new Piece(BLACK, ROOK), new Position(D, SIX))
        .withPiece(new Piece(BLACK, BISHOP), new Position(E, SIX))
        .withPiece(new Piece(WHITE, PAWN), new Position(D, FOUR))
        .withPiece(new Piece(WHITE, QUEEN), new Position(E, FOUR))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(king, initialPosition);

    assertThat(possibleMoves.size()).isEqualTo(6);
    assertThat(possibleMoves.contains(new Position(E, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(E, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, SIX))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, SIX))).isTrue();
  }

  @Test
  public void givenAFilledBoard_shouldComputePossiblePositionsForKnight() {
    Position initialPosition = new Position(E, THREE);
    Piece knight = new Piece(WHITE, KNIGHT);
    Board board = new BoardBuilder()
        .withPiece(knight, initialPosition)
        .withPiece(new Piece(BLACK, ROOK), new Position(F, FIVE))
        .withPiece(new Piece(BLACK, BISHOP), new Position(G, FOUR))
        .withPiece(new Piece(WHITE, PAWN), new Position(G, TWO))
        .withPiece(new Piece(WHITE, QUEEN), new Position(F, ONE))
        .build();

    List<Position> possibleMoves = board.getPossibleMoves(knight, initialPosition);

    assertThat(possibleMoves.size()).isEqualTo(6);
    assertThat(possibleMoves.contains(new Position(F, FIVE))).isTrue();
    assertThat(possibleMoves.contains(new Position(G, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, ONE))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, TWO))).isTrue();
    assertThat(possibleMoves.contains(new Position(C, FOUR))).isTrue();
    assertThat(possibleMoves.contains(new Position(D, FIVE))).isTrue();
  }
}