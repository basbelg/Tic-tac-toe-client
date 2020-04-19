package DataClasses;

import java.time.LocalDateTime;

public class GameInfo
{
    private String player2Username;
    private LocalDateTime startTime;
    private String gameId;

    public GameInfo(String gameId)
    {
        this.gameId = gameId;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public String getGameId() {
        return gameId;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
