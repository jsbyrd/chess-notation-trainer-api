package com.jsbyrd02.springboot.chess_notation_trainer.Game;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class GameService {

    // Store game rooms in a map
    private final Map<String, GameRoom> gameRooms = new HashMap<>();

    // Create a game room
    public GameRoom createGame(String playerId, String color) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGameId(UUID.randomUUID().toString());
        gameRoom.setPlayer1Id(playerId);
        gameRoom.setPlayer1Color(color);
        gameRooms.put(gameRoom.getGameId(), gameRoom);
        return gameRoom;
    }

    // Join a game room
    public GameRoom joinGame(String playerId, String gameId) {
        GameRoom gameRoom = gameRooms.get(gameId);

        // Handle potential errors
        if (gameRoom == null) throw new GameException("A game with gameId " + gameId + " does not exist.");
        if (gameRoom.getPlayer2Id() != null) throw new GameException("The game with gameId " + gameId + "is full.");

        // Let player 2 join
        String player1Color = gameRoom.getPlayer1Color();
        gameRoom.setPlayer2Id(playerId);
        gameRoom.setPlayer2Color(Objects.equals(player1Color, "white") ? "black" : "white");
        return gameRoom;
    }

    public GameRoom getGame(String gameId) {
        GameRoom gameRoom = gameRooms.get(gameId);
        if (gameRoom == null) throw new GameException("A game with gameId " + gameId + " does not exist.");
        return gameRoom;
    }

    public GameRoom findGameByPlayer(String playerId) {
        return gameRooms.values().stream()
                .filter(gameRoom -> gameRoom.getPlayer1Id().equals(playerId) || gameRoom.getPlayer2Id().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public void removeGame(String gameId) {
        gameRooms.remove(gameId);
    }
}
