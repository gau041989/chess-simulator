package com.gp.chess.api;

import static com.gp.chess.domain.cell.Position.from;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.gp.chess.api.request.Move;
import com.gp.chess.api.response.GameData;
import com.gp.chess.api.response.Piece;
import com.gp.chess.domain.GameFacade;
import com.gp.chess.domain.cell.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private final GameFacade gameFacade;

  @Autowired
  public GameService(GameFacade gameFacade) {
    this.gameFacade = gameFacade;
  }

  public GameData getGameData() {
    Map<Position, com.gp.chess.domain.character.Piece> piecePositions = gameFacade
        .getPiecePositions();
    return new GameData(toState(piecePositions), toKilledPieces(gameFacade.getKilledPieces()));
  }

  public GameData makeAMove(Move move) {
    Map<Position, com.gp.chess.domain.character.Piece> piecePositions =
        gameFacade.movePieceFrom(from(move.getSource()), from(move.getDestination()));
    return new GameData(toState(piecePositions), toKilledPieces(gameFacade.getKilledPieces()));
  }

  private List<Piece> toKilledPieces(List<com.gp.chess.domain.character.Piece> killedDomainPieces) {
    return killedDomainPieces.stream()
        .map(p -> new Piece(p.getColor().name(), p.getPieceType().name(), emptyList()))
        .collect(toList());
  }

  private Map<String, Piece> toState(Map<Position, com.gp.chess.domain.character.Piece> piecePositions) {
    return piecePositions
        .entrySet()
        .stream()
        .collect(
            Collectors.toMap(
                e -> e.getKey().toString(),
                e -> {
                  List<Position> possibleMoves = gameFacade.getPossibleMoves(e.getValue(), e.getKey());
                  return new Piece(
                      e.getValue().getColor().name(),
                      e.getValue().getPieceType().name(),
                      possibleMoves.stream().map(Position::toString).collect(toList())
                  );
                }
            )
        );
  }
}
