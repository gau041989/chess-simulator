package com.gp.chess.domain.cell;

import com.gp.chess.domain.exception.InvalidPositionException;
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

  public static Position from(String position) {
    try {
      String source = position.toUpperCase();
      Column sourceCol = Column.valueOf(String.valueOf(source.charAt(0)));
      Row sourceRow = Row.fromValue(Integer.parseInt(String.valueOf(source.charAt(1))));
      return new Position(sourceCol, sourceRow);
    } catch (Exception e) {
      throw new InvalidPositionException();
    }
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

  @Override
  public String toString() {
    return "" + column + ((Row)row).getValue();
  }
}
