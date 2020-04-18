package Messages;

import java.io.Serializable;

public class NewLobbyMessage implements Serializable {
    private String creatorUsername;
    private String gameLobbyId;

    public NewLobbyMessage(String creatorUsername, String gameLobbyId) {
        this.creatorUsername = creatorUsername;
        this.gameLobbyId = gameLobbyId;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }
}
