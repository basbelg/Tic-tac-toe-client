package Messages;

import java.io.Serializable;

public class GameResultMessage implements Serializable {
    boolean isPlayer1Win;
    boolean isPlayer2Win;

    public GameResultMessage(boolean isPlayer1Win, boolean isPlayer2Win) {
        this.isPlayer1Win = isPlayer1Win;
        this.isPlayer2Win = isPlayer2Win;
    }

    @Override
    public String toString() {
        return (isPlayer1Win ? "Player 1 Won the Game!" :
               (isPlayer2Win ? "Player 2 Won the Game!" : "It's a Tie!"));
    }
}
