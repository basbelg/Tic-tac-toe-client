package Messages;

import DataClasses.User;

import java.io.Serializable;

public class ConnectToLobbyMessage implements Serializable {
    private String lobbyGameId;
    private User player2;

    public ConnectToLobbyMessage(String lobbyGameId, User player2) {
        this.lobbyGameId = lobbyGameId;
        this.player2 = player2;
    }

    public String getLobbyGameId() {
        return lobbyGameId;
    }

    public User getPlayer2() {
        return player2;
    }
}
