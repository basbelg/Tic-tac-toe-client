package TicTacToe;

import GameInterfaces.Board;

public class TTTBoard implements Board {
    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public void clearBoard() {

    }

    @Override
    public boolean hasMovesLeft() {
        return false;
    }

    @Override
    public int getWinningPlayer() {
        return 0;
    }

    @Override
    public int getPlayerAt(int row, int col) throws Exception {
        return 0;
    }

    @Override
    public void setPosition(int player, int row, int col) throws Exception {

    }

    @Override
    public void unsetPosition(int row, int col) throws Exception {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }
}
