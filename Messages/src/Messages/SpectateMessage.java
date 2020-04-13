package Messages;

import GameInterfaces.Game;

public class SpectateMessage {
    private String gameId;
    private Game gameToSpectate;

    public SpectateMessage(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public Game getGameToSpectate() {
        return gameToSpectate;
    }

    public void setGameToSpectate(Game gameToSpectate) {
        this.gameToSpectate = gameToSpectate;
    }
}
