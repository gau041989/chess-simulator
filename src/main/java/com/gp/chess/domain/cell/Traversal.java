package com.gp.chess.domain.cell;

import java.util.Optional;

public interface Traversal<T extends Traversal<T>> {
  Optional<Traversal<T>> next();

  Optional<Traversal<T>> prev();
}
