package Messages;

import GameInterfaces.Game;

import java.io.Serializable;

public class CreateAIGameMessage implements Serializable {
    private Game newAIGame; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)

    public CreateAIGameMessage(Game newAIGame) {
        this.newAIGame = newAIGame;
    }

    public Game getNewAIGame() {
        return newAIGame;
    }
}
