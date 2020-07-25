package com.gp.chess;

import java.util.Optional;

public interface Traversal<T extends Traversal<T>> {
  Optional<Traversal<T>> next();

  Optional<Traversal<T>> prev();
}
