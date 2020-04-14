package DataClasses;

import java.sql.Time;
import java.time.LocalDateTime;

public class Move
{
    private int player;
    private int row;
    private int col;
    private LocalDateTime timeMade;

    public Move(int player, int row, int col)
    {
        timeMade = LocalDateTime.now();
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public LocalDateTime getTimeMade() {
        return timeMade;
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
