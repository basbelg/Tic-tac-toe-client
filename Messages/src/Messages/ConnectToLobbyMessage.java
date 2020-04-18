package Messages;

import DataClasses.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ConnectToLobbyMessage implements Serializable {
    private String lobbyGameId;
    private User player2;
    private LocalDateTime startTime;

    public ConnectToLobbyMessage(String lobbyGameId, User player2, LocalDateTime startTime) {
        this.lobbyGameId = lobbyGameId;
        this.player2 = player2;
        this.startTime = startTime;
    }

    public String getLobbyGameId() {
        return lobbyGameId;
    }

    public User getPlayer2() {
        return player2;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
