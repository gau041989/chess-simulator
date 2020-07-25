package com.gp.chess;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ColumnTest {

  @Test
  public void shouldGetNextColumn_WhenAvailable() {
    Column column = Column.A;

    assertThat(column.next().isPresent()).isTrue();
    assertThat(column.next().get()).isEqualTo(Column.B);
  }

  @Test
  public void shouldNotGetNextColumn_WhenNotAvailable() {
    Column column = Column.H;

    assertThat(column.next().isPresent()).isFalse();
  }

  @Test
  public void shouldGetPreviousColumn_WhenAvailable() {
    Column column = Column.H;

    assertThat(column.prev().isPresent()).isTrue();
    assertThat(column.prev().get()).isEqualTo(Column.G);
  }

  @Test
  public void shouldNotGetPreviousColumn_WhenNotAvailable() {
    Column column = Column.A;

    assertThat(column.prev().isPresent()).isFalse();
  }
}