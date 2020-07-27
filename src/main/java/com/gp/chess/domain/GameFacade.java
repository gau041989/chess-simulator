package com.gp.chess.domain;

import static com.gp.chess.domain.cell.Column.A;
import static com.gp.chess.domain.cell.Column.B;
import static com.gp.chess.domain.cell.Column.C;
import static com.gp.chess.domain.cell.Column.D;
import static com.gp.chess.domain.cell.Column.E;
import static com.gp.chess.domain.cell.Column.F;
import static com.gp.chess.domain.cell.Column.G;
import static com.gp.chess.domain.cell.Column.H;
import static com.gp.chess.domain.cell.Row.EIGHT;
import static com.gp.chess.domain.cell.Row.ONE;
import static com.gp.chess.domain.cell.Row.SEVEN;
import static com.gp.chess.domain.cell.Row.TWO;
import static com.gp.chess.domain.character.Color.BLACK;
import static com.gp.chess.domain.character.Color.WHITE;
import static com.gp.chess.domain.character.PieceType.BISHOP;
import static com.gp.chess.domain.character.PieceType.KING;
import static com.gp.chess.domain.character.PieceType.KNIGHT;
import static com.gp.chess.domain.character.PieceType.PAWN;
import static com.gp.chess.domain.character.PieceType.QUEEN;
import static com.gp.chess.domain.character.PieceType.ROOK;
import static java.util.stream.Collectors.toList;

import com.gp.chess.domain.Board.BoardBuilder;
import com.gp.chess.domain.action.BoardAction;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.character.Piece;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameFacade {

  private final Board board;

  @Autowired
  public GameFacade() {
    this.board = initializeBoard();
  }

  private Board initializeBoard() {
    return new BoardBuilder()
        .withPiece(new Piece(WHITE, ROOK), new Position(A, ONE))
        .withPiece(new Piece(WHITE, KNIGHT), new Position(B, ONE))
        .withPiece(new Piece(WHITE, BISHOP), new Position(C, ONE))
        .withPiece(new Piece(WHITE, QUEEN), new Position(D, ONE))
        .withPiece(new Piece(WHITE, KING), new Position(E, ONE))
        .withPiece(new Piece(WHITE, BISHOP), new Position(F, ONE))
        .withPiece(new Piece(WHITE, KNIGHT), new Position(G, ONE))
        .withPiece(new Piece(WHITE, ROOK), new Position(H, ONE))
        .withPiece(new Piece(WHITE, PAWN), new Position(A, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(B, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(C, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(D, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(E, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(F, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(G, TWO))
        .withPiece(new Piece(WHITE, PAWN), new Position(H, TWO))

        .withPiece(new Piece(BLACK, PAWN), new Position(A, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(B, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(C, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(D, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(E, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(F, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(G, SEVEN))
        .withPiece(new Piece(BLACK, PAWN), new Position(H, SEVEN))
        .withPiece(new Piece(BLACK, ROOK), new Position(A, EIGHT))
        .withPiece(new Piece(BLACK, KNIGHT), new Position(B, EIGHT))
        .withPiece(new Piece(BLACK, BISHOP), new Position(C, EIGHT))
        .withPiece(new Piece(BLACK, QUEEN), new Position(D, EIGHT))
        .withPiece(new Piece(BLACK, KING), new Position(E, EIGHT))
        .withPiece(new Piece(BLACK, BISHOP), new Position(F, EIGHT))
        .withPiece(new Piece(BLACK, KNIGHT), new Position(G, EIGHT))
        .withPiece(new Piece(BLACK, ROOK), new Position(H, EIGHT))

        .build();
  }

  public Map<Position, Piece> getPiecePositions() {
    return board.getPiecePositions();
  }

  public List<Position> getPossibleMoves(Piece piece, Position position) {
    return board.getPossibleMoves(piece, position)
        .stream()
        .map(BoardAction::getPosition)
        .collect(toList());
  }
}
