package Messages;

import GameInterfaces.Game;

public class CreateLobbyMessage {
    private Game newTTTGame;

    public CreateLobbyMessage(Game newTTTGame) {
        this.newTTTGame = newTTTGame;
    }

    public Game getNewTTTGame() {
        return newTTTGame;
    }
}
