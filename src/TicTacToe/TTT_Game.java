package TicTacToe;

import GameInterfaces.Board;
import GameInterfaces.Game;

import java.util.List;

public class TTT_Game implements Game {
    @Override
    public void startGame() throws Exception {

    }

    @Override
    public void endGame() throws Exception {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void saveGame() throws Exception {

    }

    @Override
    public String getGameId() {
        return null;
    }

    @Override
    public void performMove(Move nextMove) throws Exception {

    }

    @Override
    public void undoMove() throws Exception {

    }

    @Override
    public void restart() throws Exception {

    }

    @Override
    public int getNextPlayer() {
        return 0;
    }

    @Override
    public int getWinner() {
        return 0;
    }

    @Override
    public <T extends Move> List<T> getMoveHistory() {
        return null;
    }

    @Override
    public Board getBoard() throws Exception {
        return null;
    }

    @Override
    public void addGameListener(GameListener listener) {

    }

    @Override
    public void removeGameListener(GameListener listener) {

    }
}
