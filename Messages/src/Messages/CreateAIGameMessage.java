package Messages;

import TicTacToe.TTT_Game;

import java.io.Serializable;

public class CreateAIGameMessage implements Serializable {
    private TTT_Game newAIGame; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)

    public CreateAIGameMessage(TTT_Game newAIGame) {
        this.newAIGame = newAIGame;
    }

    public TTT_Game getNewAIGame() {
        return newAIGame;
    }
}
