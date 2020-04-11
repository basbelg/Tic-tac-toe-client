package Game;

public class Move
{
    private String playerID;
    private int row;
    private int col;

    public Move(String playerID, int row, int col)
    {
        this.playerID = playerID;
        this.row = row;
        this.col = col;
    }

    public String getPlayerID()
    {
        return playerID;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
}
