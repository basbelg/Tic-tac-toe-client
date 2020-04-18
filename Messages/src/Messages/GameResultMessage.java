package Messages;

import java.io.Serializable;

public class GameResultMessage implements Serializable {
    boolean isPlayer1Win;
    boolean isPlayer2Win;

    public GameResultMessage() {
    }

    public void setPlayer1Win(boolean player1Win) {
        isPlayer1Win = player1Win;
    }

    public void setPlayer2Win(boolean player2Win) {
        isPlayer2Win = player2Win;
    }

    @Override
    public String toString() {
        return (isPlayer1Win ? "Player 1 Won the Game!" :
               (isPlayer2Win ? "Player 2 Won the Game!" : "It's a Tie!"));
    }
}
