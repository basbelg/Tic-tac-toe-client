package TicTacToe;

import GameInterfaces.Move;

public class TTT_Move implements Move
{
    private String userID;
    private int player;
    private int row;
    private int col;

    public TTT_Move(String userID, int player, int row, int col) {
        this.userID = userID;
        this.player = player;
        this.row = row;
        this.col = col;
    }

    @Override
    public String getUserID() {return userID;}

    @Override
    public int getPlayer() {return player;}

    @Override
    public int getRow() {return row;}

    @Override
    public int getColumn() {return col;}
}
