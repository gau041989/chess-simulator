package com.gp.chess.domain.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.gp.chess.domain.exception.InvalidPositionException;
import org.junit.jupiter.api.Test;

class PositionTest {

  @Test
  public void givenPositionInFormOfString_shouldDerive() {
    Position position = Position.from("D7");

    assertThat(position.getColumn()).isEqualTo(Column.D);
    assertThat(position.getRow()).isEqualTo(Row.SEVEN);
  }

  @Test
  public void givenPositionInFormOfLowerCaseString_shouldDerive() {
    Position position = Position.from("d7");

    assertThat(position.getColumn()).isEqualTo(Column.D);
    assertThat(position.getRow()).isEqualTo(Row.SEVEN);
  }

  @Test
  public void givenInvalidColumn_shouldThrow() {
    assertThatExceptionOfType(InvalidPositionException.class)
        .isThrownBy(() -> Position.from("Z2"));
  }

  @Test
  public void givenInvalidRow_shouldThrow() {
    assertThatExceptionOfType(InvalidPositionException.class)
        .isThrownBy(() -> Position.from("B9"));
  }
}