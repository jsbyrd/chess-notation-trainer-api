package com.jsbyrd02.springboot.chess_notation_trainer.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinGameRequestDTO {
    private String gameId;
    private String playerId;
}
