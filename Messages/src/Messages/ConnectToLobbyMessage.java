package Messages;

import DataClasses.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ConnectToLobbyMessage implements Serializable {
    private String lobbyGameId;
    private User player2;
    private LocalDateTime startTime;

    public ConnectToLobbyMessage() {
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

    public void setLobbyGameId(String lobbyGameId) {
        this.lobbyGameId = lobbyGameId;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
