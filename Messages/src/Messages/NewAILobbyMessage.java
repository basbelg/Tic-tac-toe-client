package Messages;

import java.io.Serializable;

public class NewAILobbyMessage implements Serializable {
    private String creatorUsername;
    private String gameLobbyId;

    public NewAILobbyMessage(String creatorUsername, String gameLobbyId) {
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
