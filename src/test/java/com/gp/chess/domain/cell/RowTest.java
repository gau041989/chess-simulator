package com.gp.chess.domain.cell;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RowTest {
  @Test
  public void shouldGetNextRow_WhenAvailable() {
    Row row = Row.ONE;

    assertThat(row.next().isPresent()).isTrue();
    assertThat(row.next().get()).isEqualTo(Row.TWO);
  }

  @Test
  public void shouldNotGetNextRow_WhenNotAvailable() {
    Row row = Row.EIGHT;

    assertThat(row.next().isPresent()).isFalse();
  }

  @Test
  public void shouldGetPreviousRow_WhenAvailable() {
    Row row = Row.EIGHT;

    assertThat(row.prev().isPresent()).isTrue();
    assertThat(row.prev().get()).isEqualTo(Row.SEVEN);
  }

  @Test
  public void shouldNotGetPreviousRow_WhenNotAvailable() {
    Row row = Row.ONE;

    assertThat(row.prev().isPresent()).isFalse();
  }
}