package Controllers;

import Client.Client;
import DataClasses.GameInfo;
import Messages.GameLogMessage;
import Messages.GamesPlayedMessage;
import Messages.MessageFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameHistoriesController implements BaseController, Initializable
{
    private Client client;
    public ListView gameList;
    public Button backButton;
    public Button viewMoveHistoryButton;
    public Label errorLabel;
    private List<GameInfo> gamesPlayed = new ArrayList<>();

    public void onViewMoveHistoryClicked()
    {
        try
        {
            if(gameList.getItems().size() <= 0)
            {
                errorLabel.setText("There are no games to view!");
                errorLabel.setVisible(true);
            }
            else if(gameList.getSelectionModel().getSelectedIndex() < 0)
            {
                errorLabel.setText("Please select a game to view!");
                errorLabel.setVisible(true);
            }
            else if(gameList.getSelectionModel().getSelectedIndex() >= 0)
            {
                //send in move history of game selected
                waitingForServer();
                GameLogMessage glm = (GameLogMessage) MessageFactory.getMessage("GLG-MSG");
                glm.setUserId(client.getUser().getId());
                glm.setGameId(gamesPlayed.get(gameList.getSelectionModel().getSelectedIndex()).getGameId());
                client.update(glm);
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
            ac.passInfo(client);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Account");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg)
    {
        Platform.runLater(() -> {
            finishedWaitingForServer();
            Label label = null;
            if (msg instanceof GameLogMessage) {
                if (((GameLogMessage) msg).getMoveHistory() != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/MoveHistory.fxml"));
                        Parent root = loader.load();
                        MoveHistoryController mhc = loader.getController();
                        mhc.passInfo(client, (GameLogMessage) msg);
                        Stage stage = (Stage) viewMoveHistoryButton.getScene().getWindow();
                        stage.close();
                        stage.setTitle("Move History");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (msg instanceof GamesPlayedMessage)
            {
                gamesPlayed = ((GamesPlayedMessage) msg).getGameInfoList();
                if(gamesPlayed.size() != 0)
                {
                    for (int i = 0, max_idx; i < gamesPlayed.size() - 1; i++)
                    {
                        // Find the minimum element in unsorted array
                        max_idx = i;
                        for (int j = i + 1; j < gamesPlayed.size(); j++)
                        {
                            if (gamesPlayed.get(j).getStartTime().isAfter(gamesPlayed.get(max_idx).getStartTime()))
                            {
                                max_idx = j;
                            }

                        }
                        GameInfo temp = gamesPlayed.get(max_idx);
                        gamesPlayed.set(max_idx, gamesPlayed.get(i));
                        gamesPlayed.set(i, temp);
                    }

                    for(GameInfo gi : gamesPlayed)
                    {
                        StringBuffer out = new StringBuffer();

                        out.append(String.format("VS. %-19s", gi.getPlayer2Username()) + "\t\tTime Started: " + (gi.getStartTime().getMonth().toString()) + " " +
                                    gi.getStartTime().getDayOfMonth() + ", " + gi.getStartTime().getYear() + " at " + (gi.getStartTime().getHour() < 10 ? ("0" + gi.getStartTime().getHour()) : gi.getStartTime().getHour()) +
                                    ":" + (gi.getStartTime().getMinute() < 10 ? ("0" + gi.getStartTime().getMinute()) : gi.getStartTime().getMinute()) +
                                    ":" + (gi.getStartTime().getSecond() < 10 ? ("0" + gi.getStartTime().getSecond()) : gi.getStartTime().getSecond()));

                        label = new Label(out.toString());
                        gameList.getItems().add(label);
                    }
                }
                else
                {
                    label = new Label("NO GAMES PLAYED");
                    gameList.getItems().add(label);
                }
            }
        });
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);

        errorLabel.setVisible(false);
        waitingForServer();
        GamesPlayedMessage gpm = (GamesPlayedMessage) MessageFactory.getMessage("GMP-MSG");
        gpm.setPlayerId(client.getUser().getId());
        client.update(gpm);
    }

    private void waitingForServer()
    {
        backButton.setDisable(true);
        viewMoveHistoryButton.setDisable(true);
    }

    private void finishedWaitingForServer()
    {
        backButton.setDisable(false);
        viewMoveHistoryButton.setDisable(false);
    }

    public void onGameListClicked()
    {
        errorLabel.setVisible(false);
    }
}
