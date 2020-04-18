package Messages;

import GameInterfaces.Move;

public class LegalMoveMessage {
    private Move nextMove;

    public LegalMoveMessage(Move nextMove) {
        this.nextMove = nextMove;
    }

    public Move getNextMove() {
        return nextMove;
    }
}
