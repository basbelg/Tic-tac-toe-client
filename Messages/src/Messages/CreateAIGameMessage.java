package Messages;

import GameInterfaces.Game;

public class CreateAIGameMessage {
    private Game newAIGame; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)

    public CreateAIGameMessage(Game newAIGame) {
        this.newAIGame = newAIGame;
    }
}
