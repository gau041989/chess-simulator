package com.gp.chess;

import java.util.Objects;

public class Position {
  private final Column column;
  private final Row row;

  public Position(Column column, Row row) {
    this.column = column;
    this.row = row;
  }

  public Column getColumn() {
    return column;
  }

  public Row getRow() {
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
