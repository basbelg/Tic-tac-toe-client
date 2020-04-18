package Messages;

import GameInterfaces.Move;

public class LegalMoveMessage {
    private Move nextMove;

    public LegalMoveMessage() {
    }

    public Move getNextMove() {
        return nextMove;
    }

    public void setNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }
}
