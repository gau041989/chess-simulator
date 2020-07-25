package com.gp.chess;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum Row implements Traversal<Row> {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8);

  private final int value;

  Row(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  private static final Map<Integer, Row> LOOK_UP = Arrays.stream(values())
      .collect(toMap(Row::getValue, Function.identity()));

  public Optional<Traversal<Row>> next() {
    return LOOK_UP.containsKey(this.getValue() + 1)
        ? Optional.of(LOOK_UP.get(this.getValue() + 1))
        : Optional.empty();
  }

  public Optional<Traversal<Row>> prev() {
    return LOOK_UP.containsKey(this.getValue() - 1)
        ? Optional.of(LOOK_UP.get(this.getValue() - 1))
        : Optional.empty();
  }
}