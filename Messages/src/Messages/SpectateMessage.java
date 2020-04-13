package Messages;

import GameInterfaces.Game;

public class SpectateMessage {
    private String gameId;
    private Game gameToSpectate;

    public SpectateMessage(String gameId) {
        this.gameId = gameId;
    }
}
