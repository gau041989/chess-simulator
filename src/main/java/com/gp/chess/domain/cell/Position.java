package com.gp.chess.domain.cell;

import java.util.Objects;

public class Position {
  private final Traversal<Column> column;
  private final Traversal<Row> row;

  public Position(Traversal<Column> column, Traversal<Row> row) {
    this.column = column;
    this.row = row;
  }

  public Traversal<Column> getColumn() {
    return column;
  }

  public Traversal<Row> getRow() {
    return row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return column == position.column &&
        row == position.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }
}
