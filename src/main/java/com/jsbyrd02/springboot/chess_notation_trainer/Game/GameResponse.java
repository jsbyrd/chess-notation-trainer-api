package com.jsbyrd02.springboot.chess_notation_trainer.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private GAME_STATE gameState;
    private String gameId;
    private String player1Id;
    private String player1Color;
    private String player2Id;
    private String player2Color;
    private String move;
    private String error;
}
