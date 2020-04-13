package GameInterfaces;

import java.io.Serializable;
import java.util.List;
import Game.Move;

    public interface Game extends Serializable {
        void startGame() throws Exception;
        void endGame() throws Exception;
        boolean isFinished();
        void saveGame() throws Exception;

        String getGameId();

        void performMove(Move nextMove) throws Exception;
        void undoMove() throws Exception;
        void restart() throws Exception;

        int getNextPlayer();
        int getWinner() throws Exception;

        <T extends Move> List<Move> getMoveHistory();

        Board getBoard() throws Exception;

      //  void addGameListener(GameListener listener);
        //void removeGameListener(GameListener listener);
    }

