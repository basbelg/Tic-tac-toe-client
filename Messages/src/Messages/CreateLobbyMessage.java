package Messages;

import java.io.Serializable;

public class CreateLobbyMessage implements Serializable {
    private int player1Id;
    private String gameLobbyId;

    public CreateLobbyMessage(int player1Id, String gameLobbyId) {
        this.player1Id = player1Id;
        this.gameLobbyId = gameLobbyId;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }
}
