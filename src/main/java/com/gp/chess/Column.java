package com.gp.chess;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum Column implements Traversal<Column> {
  A(1),
  B(2),
  C(3),
  D(4),
  E(5),
  F(6),
  G(7),
  H(8);

  private final int value;

  Column(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  private static final Map<Integer, Column> LOOK_UP = Arrays.stream(values())
      .collect(toMap(Column::getValue, Function.identity()));

  public Optional<Traversal<Column>> next() {
    return LOOK_UP.containsKey(this.getValue() + 1)
        ? Optional.of(LOOK_UP.get(this.getValue() + 1))
        : Optional.empty();
  }

  public Optional<Traversal<Column>> prev() {
    return LOOK_UP.containsKey(this.getValue() - 1)
        ? Optional.of(LOOK_UP.get(this.getValue() - 1))
        : Optional.empty();
  }
}