package Messages;

import TicTacToe.TTT_Game;

import java.io.Serializable;

public class CreateLobbyMessage implements Serializable {
    private TTT_Game newTTTGame;

    public CreateLobbyMessage(TTT_Game newTTTGame) {
        this.newTTTGame = newTTTGame;
    }

    public TTT_Game getNewTTTGame() {
        return newTTTGame;
    }
}
