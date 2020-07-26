package com.gp.chess.api;

import static java.util.stream.Collectors.toList;

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
    Map<String, Piece> board = gameFacade
        .getPiecePositions()
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
    return new GameData(board);
  }
}
