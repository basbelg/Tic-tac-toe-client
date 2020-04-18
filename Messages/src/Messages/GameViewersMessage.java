package Messages;

import DataClasses.Spectator;

import java.io.Serializable;
import java.util.List;

public class GameViewersMessage implements Serializable {
    private int userId;
    private String gameId;
    private List<Spectator> spectators;

    public GameViewersMessage(int userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
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
