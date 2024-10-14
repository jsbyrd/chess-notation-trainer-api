package com.jsbyrd02.chess_notation_trainer.Game.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameEndMessage {
    private String winnerId;
    private String message;
}