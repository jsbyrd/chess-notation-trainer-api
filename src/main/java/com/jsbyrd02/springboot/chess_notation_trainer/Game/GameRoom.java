package com.jsbyrd02.springboot.chess_notation_trainer.Game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRoom {
    private String gameId;
    private String player1Id;
    private String player1Color;
    private String player2Id;
    private String player2Color;
    private String fen;

    public String getOpponentId(String playerId) {
        if (player1Id.equals(playerId)) {
            return player2Id;
        } else if (player2Id.equals(playerId)) {
            return player1Id;
        }
        return null;
    }
}
