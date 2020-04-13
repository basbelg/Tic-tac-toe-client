package Messages;

import GameInterfaces.Game;

import java.io.Serializable;

public class SpectateMessage implements Serializable {
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
