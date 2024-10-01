package com.jsbyrd02.springboot.chess_notation_trainer.config;

import com.jsbyrd02.springboot.chess_notation_trainer.Game.GameRoom;
import com.jsbyrd02.springboot.chess_notation_trainer.Game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameService gameService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        System.out.println("HELLO WORLD");
        log.info("session attributes: {}", headerAccessor.getSessionAttributes());

        if (username != null) {
            log.info("User {} disconnected", username);

            // Get the game the user is part of
            GameRoom gameRoom = gameService.findGameByPlayer(username);

            if (gameRoom != null) {
                log.info("Found game room {}", gameRoom.getGameId());
                // Remove the game from gameRooms
                gameService.removeGame(gameRoom.getGameId());

                // Notify the other player about the disconnection
                String opponentId = gameRoom.getOpponentId(username);
                if (opponentId != null) {
                    log.info("Sending notification to opponent {}", opponentId);
                    messagingTemplate.convertAndSend("/topic/game-disconnect/" + opponentId,
                            "Your opponent has disconnected. You win!");
                }
            }
        }
    }
}
