package Messages;

import java.io.Serializable;

public class FullLobbyMessage implements Serializable {
    private String lobbyGameId;

    public FullLobbyMessage(String lobbyGameId) {
        this.lobbyGameId = lobbyGameId;
    }

    public String getLobbyGameId() {
        return lobbyGameId;
    }
}
