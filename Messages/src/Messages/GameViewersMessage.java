package Messages;

import DataClasses.Spectator;

import java.util.List;

public class GameViewersMessage {
    private String gameId;
    private List<Spectator> spectators;

    public GameViewersMessage(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    public void setSpectators(List<Spectator> spectators) {
        this.spectators = spectators;
    }
}
