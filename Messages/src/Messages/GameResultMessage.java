package Messages;

import java.io.Serializable;

public class GameResultMessage implements Serializable {
    boolean isPlayer1Win;

    public GameResultMessage(boolean isPlayer1Win) {
        this.isPlayer1Win = isPlayer1Win;
    }

    @Override
    public String toString() {
        return (isPlayer1Win ? "Player 1 Won the Game!" : "Player 2 Won the Game!");
    }
}