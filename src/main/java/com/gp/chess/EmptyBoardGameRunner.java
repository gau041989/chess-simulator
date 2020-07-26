package com.gp.chess;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import com.gp.chess.domain.Board.BoardBuilder;
import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.character.Color;
import com.gp.chess.domain.character.Piece;
import com.gp.chess.domain.character.PieceType;
import java.util.List;
import java.util.Scanner;

public class EmptyBoardGameRunner {

  private void executeAndPrint(String command) {
    if(command != null) {
      String upperCaseCommand = command.toUpperCase();
      String[] split = upperCaseCommand.trim().split(" ");
      if(split.length < 2) {
        throw new RuntimeException("Need a piece and expected position to simulate");
      }
      if(split[1].length() < 2) {
        throw new RuntimeException("Need expected position in proper format to simulate. For example E3");
      }
      PieceType pieceType = PieceType.valueOf(split[0]);
      Piece piece = new Piece(Color.WHITE, pieceType);
      Position position = new Position(
          Column.valueOf(valueOf(split[1].charAt(0))),
          Row.fromValue(parseInt(valueOf(split[1].charAt(1))))
      );

      List<Position> possibleMoves = new BoardBuilder()
          .withPiece(piece, position)
          .build()
          .getPossibleMoves(piece, position);
      System.out.println(possibleMoves);
    }
  }

  public static void main(String[] args) {
    EmptyBoardGameRunner runner = new EmptyBoardGameRunner();
    System.out.print("Unique pieces available are ROOK, KNIGHT, BISHOP, QUEEN, KING and PAWN");
    System.out.print("\nExecute :- ");
    Scanner scanner = new Scanner(System.in);
    String command = scanner.nextLine();
    try {
      runner.executeAndPrint(command);
    } catch (Exception e) {
      System.out.println("Error :- " + e.getMessage());
    }
  }
}
