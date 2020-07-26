package com.gp.chess.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.gp.*")
@SpringBootApplication
public class ChessGameApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChessGameApplication.class, args);
  }

}
