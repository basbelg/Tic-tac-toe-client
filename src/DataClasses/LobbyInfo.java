package DataClasses;

public class LobbyInfo {
    private String creatorUsername;
    private String lobbyId;
    private int playerCount;

    public LobbyInfo(String creatorUsername, String lobbyId, int playerCount) {
        this.creatorUsername = creatorUsername;
        this.lobbyId = lobbyId;
        this.playerCount = playerCount;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
