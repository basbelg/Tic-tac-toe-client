package Messages;

import GameInterfaces.Game;

import java.io.Serializable;

public class CreateLobbyMessage implements Serializable {
    private Game newTTTGame;

    public CreateLobbyMessage(Game newTTTGame) {
        this.newTTTGame = newTTTGame;
    }

    public Game getNewTTTGame() {
        return newTTTGame;
    }
}
