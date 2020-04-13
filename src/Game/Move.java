package Game;

public class Move
{
    private String playerID;
    private int player;
    private int row;
    private int col;

    public Move(String playerID, int player, int row, int col)
    {
        this.playerID = playerID;
        this.player = player;
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

    public int getColumn()
    {
        return col;
    }

    public int getPlayer()
    {
        return player;
    }
}
