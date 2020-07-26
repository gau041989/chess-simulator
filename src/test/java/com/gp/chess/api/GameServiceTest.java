package com.gp.chess.api;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.gp.chess.api.response.GameData;
import com.gp.chess.domain.GameFacade;
import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.character.Color;
import com.gp.chess.domain.character.Piece;
import com.gp.chess.domain.character.PieceType;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock
  private GameFacade gameFacade;
  @InjectMocks
  private GameService gameService;

  @Test
  public void givenBoard_shouldComputeGameData() {
    final Position pawnPosition = new Position(Column.E, Row.THREE);
    final Piece pawnPiece = new Piece(Color.WHITE, PieceType.PAWN);
    given(gameFacade.getPiecePositions())
        .willReturn(new HashMap<>() {{
          put(pawnPosition, pawnPiece);
        }});
    given(gameFacade.getPossibleMoves(pawnPiece, pawnPosition))
        .willReturn(asList(new Position(Column.E, Row.FOUR), new Position(Column.F, Row.FOUR)));

    GameData gameData = gameService.getGameData();

    assertThat(gameData.getBoard().size()).isEqualTo(1);
    com.gp.chess.api.response.Piece response = gameData.getBoard().get("E3");
    assertThat(response.getColor()).isEqualTo("WHITE");
    assertThat(response.getType()).isEqualTo("PAWN");
    assertThat(response.getPossibleMoves()).isEqualTo(asList("E4", "F4"));
  }
}