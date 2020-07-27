package com.gp.chess.domain.action;

import com.gp.chess.domain.cell.Column;
import com.gp.chess.domain.cell.Position;
import com.gp.chess.domain.cell.Row;
import com.gp.chess.domain.cell.Traversal;
import java.util.Objects;

public class BoardAction {

  private final ActionType actionType;
  private final Position position;

  public BoardAction(ActionType actionType, Position position) {
    this.actionType = actionType;
    this.position = position;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public Position getPosition() {
    return position;
  }

  public static BoardAction from(Traversal<Column> col, Traversal<Row> row, ActionType actionType) {
    return new BoardAction(actionType, new Position(col, row));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoardAction that = (BoardAction) o;
    return actionType == that.actionType &&
        position.equals(that.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionType, position);
  }
}
