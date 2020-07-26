package com.gp.chess.api;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gp.chess.api.response.GameData;
import com.gp.chess.api.response.Piece;
import com.gp.chess.domain.ChessGameApplication;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ChessGameApplication.class)
class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ResourceLoader resourceLoader;

  @MockBean
  private GameService gameService;

  @Test
  public void givenBoardWithSomePieces_shouldGetItsState() throws Exception {
    // given
    given(gameService.getGameData())
        .willReturn(new GameData(
            new HashMap<>() {{
              put("A2", new Piece("WHITE", "PAWN", asList("A3", "A4")));
            }})
        );

    // when
    var mvcResult =
        mockMvc
            .perform(
                get("/v1/game")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

    // then
    var expectedResponse =
        getResourceAsString("classpath:contracts/game-data.json");
    var response = mvcResult.getResponse().getContentAsString();
    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.STRICT);
  }

  private String getResourceAsString(String resourcePath) throws IOException {
    var resource = resourceLoader.getResource(resourcePath);
    var file = resource.getFile();
    return Files.readString(Path.of(file.getPath()));
  }
}