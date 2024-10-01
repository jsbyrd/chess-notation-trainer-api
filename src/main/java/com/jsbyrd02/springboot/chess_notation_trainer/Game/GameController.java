package com.jsbyrd02.springboot.chess_notation_trainer.Game;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<GameResponse> create(@RequestBody CreateGameRequestDTO request) {
        log.info("Create game request: {} {}", request.getPlayerId(), request.getColor());

        GameRoom gameRoom = gameService.createGame(request.getPlayerId(), request.getColor());

        GameResponse gameResponse = new GameResponse();
        gameResponse.setGameState(GAME_STATE.WAITING);
        gameResponse.setPlayer1Id(request.getPlayerId());
        gameResponse.setPlayer1Color(request.getColor());
        gameResponse.setGameId(gameRoom.getGameId());

        log.info("Game created: {}", gameRoom.getGameId());

        messagingTemplate.convertAndSend("/topic/game-progress/" + gameRoom.getGameId(), gameResponse);

        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<GameResponse> join(@RequestBody JoinGameRequestDTO request) throws GameException {
        log.info("join request: {}", request);

        GameRoom gameRoom = gameService.joinGame(request.getPlayerId(), request.getGameId());

        GameResponse gameResponse = new GameResponse();
        gameResponse.setGameState(GAME_STATE.ACTIVE);
        gameResponse.setGameId(gameRoom.getGameId());
        gameResponse.setPlayer1Id(gameRoom.getPlayer1Id());
        gameResponse.setPlayer1Color(gameRoom.getPlayer1Color());
        gameResponse.setPlayer2Id(gameRoom.getPlayer2Id());
        gameResponse.setPlayer2Color(gameRoom.getPlayer2Color());

        messagingTemplate.convertAndSend("/topic/game-progress/" + request.getGameId(), gameResponse);

        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping("/move")
    public ResponseEntity<GameResponse> makeMove(@RequestBody MakeMoveDTO move) throws  GameException {
        log.info("move: {}", move.getMove());

        GameRoom gameRoom = gameService.getGame(move.getGameId());
        GameResponse gameResponse = new GameResponse();
        gameResponse.setGameState(GAME_STATE.ACTIVE);
        gameResponse.setPlayer1Id(gameRoom.getPlayer1Id());
        gameResponse.setPlayer1Color(gameRoom.getPlayer1Color());
        gameResponse.setPlayer2Id(gameRoom.getPlayer2Id());
        gameResponse.setPlayer2Color(gameRoom.getPlayer2Color());
        gameResponse.setGameId(gameRoom.getGameId());
        gameResponse.setMove(move.getMove());

        messagingTemplate.convertAndSend("/topic/game-progress/" + move.getGameId(), gameResponse);
        return ResponseEntity.ok(gameResponse);
    }

    @MessageMapping("/game.addUser")
    @SendTo("/topic/public")
    public void addUser(@Payload JoinGameRequestDTO joinGameRequestDTO, SimpMessageHeaderAccessor headerAccessor) {
        // Add username to websocket session
        log.info("Adding user {}", joinGameRequestDTO.getPlayerId());
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", joinGameRequestDTO.getPlayerId());
    }
}