package Messages;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CreateAIGameMessage implements Serializable {
    private int player1Id;
    private String gameLobbyId; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)
    private LocalDateTime startTime;

    public CreateAIGameMessage(int player1Id, String gameLobbyId, LocalDateTime startTime) {
        this.player1Id = player1Id;
        this.gameLobbyId = gameLobbyId;
        this.startTime = startTime;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
