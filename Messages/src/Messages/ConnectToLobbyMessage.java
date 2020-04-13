package Messages;

import DataClasses.User;

public class ConnectToLobbyMessage {
    private String lobbyGameId;
    private User player2;

    public ConnectToLobbyMessage(String lobbyGameId, User player2) {
        this.lobbyGameId = lobbyGameId;
        this.player2 = player2;
    }
}
