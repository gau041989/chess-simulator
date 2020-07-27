package com.gp.chess.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gp.chess.api.request.Move;
import com.gp.chess.api.response.GameData;
import com.gp.chess.domain.exception.DomainException;
import com.gp.chess.domain.exception.InvalidMoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/game")
public class GameController {

  private final GameService gameService;

  @Autowired
  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameData> getGameData() {
    return ResponseEntity.ok(gameService.getGameData());
  }

  @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<GameData> movePiece(@RequestBody Move move) {
    return ResponseEntity.ok(gameService.makeAMove(move));
  }

  @ExceptionHandler(DomainException.class)
  public ResponseEntity handleExceptions(InvalidMoveException e) {
    return ResponseEntity.badRequest().body(e);
  }
}
