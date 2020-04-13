package Messages;

import DataClasses.Spectator;

import java.util.List;

public class GameViewersMessage {
    private String gameId;
    private List<Spectator> spectators;

    public GameViewersMessage(String gameId) {
        this.gameId = gameId;
    }
}
