package Controllers;

import Client.Client;
import Messages.GameLogMessage;
import Messages.GameViewersMessage;
import Messages.MessageFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MoveHistoryController implements BaseController, Initializable
{
    public Label moveNumLabel;
    public Button nextButton;
    public Button previousButton;
    public Button backButton;
    public GridPane board;
    public Label timeLabel;
    public Label playerLabel;
    public Label tile00;
    public Label tile01;
    public Label tile02;
    public Label tile10;
    public Label tile11;
    public Label tile12;
    public Label tile20;
    public Label tile21;
    public Label tile22;
    public Label winnerLabel;
    public Label gameStartedLabel;
    public Label gameEndedLabel;
    public Label usersGameLabel;
    private GameLogMessage glm;
    private int moveCounter = 0;
    private String turn = "X";
    private Client client;

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/GameHistories.fxml"));
            Parent root = loader.load();
            GameHistoriesController ghc = loader.getController();
            ghc.passInfo(client);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Game Histories");
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

    public void passInfo(Client client, GameLogMessage glm)
    {
        this.client = client;
        client.setController(this);
        this.glm = glm;

        usersGameLabel.setText(glm.getPlayer1Username() + "'s Game!");
        LocalDateTime time = glm.getGameStarted();
        gameStartedLabel.setText((time.getMonth().toString()) + " " +
                time.getDayOfMonth() + ", " + time.getYear() + "\n at " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()) +
                ":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()) +
                ":" + (time.getSecond() < 10 ? ("0" + time.getSecond()) : time.getSecond()));
        time = glm.getGameEnded();
        gameEndedLabel.setText((time.getMonth().toString()) + " " +
                time.getDayOfMonth() + ", " + time.getYear() + "\n at " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()) +
                ":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()) +
                ":" + (time.getSecond() < 10 ? ("0" + time.getSecond()) : time.getSecond()));

        List<Node> boardTiles = new ArrayList<>();
        boardTiles.add(tile00);
        boardTiles.add(tile01);
        boardTiles.add(tile02);
        boardTiles.add(tile10);
        boardTiles.add(tile11);
        boardTiles.add(tile12);
        boardTiles.add(tile20);
        boardTiles.add(tile21);
        boardTiles.add(tile22);

        Platform.runLater(() -> {
            if(glm.getMoveHistory().size() == 0)
            {
                winnerLabel.setText(glm.getWinner());
            }
            else if(glm.getMoveHistory().size() != 0)
            {
                placeMove();

                moveNumLabel.setText("1/" + glm.getMoveHistory().size());
                LocalDateTime move_time = glm.getMoveHistory().get(0).getTimeMade();
                timeLabel.setText((move_time.getMonth().toString()) + " " +
                        move_time.getDayOfMonth() + ", " + move_time.getYear() + "\n at " + (move_time.getHour() < 10 ? ("0" + move_time.getHour()) : move_time.getHour()) +
                        ":" + (move_time.getMinute() < 10 ? ("0" + move_time.getMinute()) : move_time.getMinute()) +
                        ":" + (move_time.getSecond() < 10 ? ("0" + move_time.getSecond()) : move_time.getSecond()));
                playerLabel.setText(glm.getPlayer1Username() + "\'s move!");
                previousButton.setDisable(true);
                if(glm.getMoveHistory().size() == 1)
                {
                    nextButton.setDisable(true);
                    winnerLabel.setText(glm.getWinner());
                }
            }
        });
    }

    public void onNextClicked()
    {
        Platform.runLater(() -> {
            if(moveCounter < glm.getMoveHistory().size())
            {
                moveCounter++;

                placeMove();
                moveNumLabel.setText((moveCounter + 1) + "/" + glm.getMoveHistory().size());
                LocalDateTime time = glm.getMoveHistory().get(moveCounter).getTimeMade();
                timeLabel.setText((time.getMonth().toString()) + " " +
                        time.getDayOfMonth() + ", " + time.getYear() + "\n at " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()) +
                        ":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()) +
                        ":" + (time.getSecond() < 10 ? ("0" + time.getSecond()) : time.getSecond()));
                playerLabel.setText((playerLabel.getText()).equals(glm.getPlayer1Username() + "\'s move!") ? (glm.getPlayer2Username() + "\'s move!") : (glm.getPlayer1Username() + "\'s move!"));

                if (moveCounter >= (glm.getMoveHistory().size() - 1)) {
                    nextButton.setDisable(true);
                    winnerLabel.setText(glm.getWinner());
                } if (previousButton.isDisable()) {
                    previousButton.setDisable(false);
                }
            }
        });
    }

    public void onPreviousClicked()
    {
        swapTurn();
        Platform.runLater(() -> {
            if(moveCounter > 0)
            {
                int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
                int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
                moveCounter--;

                if(x == 0 && y == 0)
                {
                    tile00.setText("");
                }
                else if(x == 0 && y == 1)
                {
                    tile01.setText("");
                }
                else if(x == 0 && y == 2)
                {
                    tile02.setText("");
                }
                else if(x == 1 && y == 0)
                {
                    tile10.setText("");
                }
                else if(x == 1 && y == 1)
                {
                    tile11.setText("");
                }
                else if(x == 1 && y == 2)
                {
                    tile12.setText("");
                }
                else if(x == 2 && y == 0)
                {
                    tile20.setText("");
                }
                else if(x == 2 && y == 1)
                {
                    tile21.setText("");
                }
                else if(x == 2 && y == 2)
                {
                    tile22.setText("");
                }
                moveNumLabel.setText((moveCounter + 1) + "/" + glm.getMoveHistory().size());
                LocalDateTime time = glm.getMoveHistory().get(moveCounter).getTimeMade();
                timeLabel.setText((time.getMonth().toString()) + " " +
                        time.getDayOfMonth() + ", " + time.getYear() + "\n at " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()) +
                        ":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()) +
                        ":" + (time.getSecond() < 10 ? ("0" + time.getSecond()) : time.getSecond()));
                playerLabel.setText((playerLabel.getText()).equals(glm.getPlayer1Username() + "\'s move!") ? (glm.getPlayer2Username() + "\'s move!") : (glm.getPlayer1Username() + "\'s move!"));

                if (moveCounter <= 0) {
                    previousButton.setDisable(true);
                } if (nextButton.isDisable()) {
                    nextButton.setDisable(false);
                    winnerLabel.setText("");
                }
            }
        });
    }

    public void onSpectatorsClicked()
    {
        GameViewersMessage gvm = (GameViewersMessage) MessageFactory.getMessage("GVW-MSG");
        gvm.setUserId(glm.getUserId());
        gvm.setGameId(glm.getGameId());
        client.update(gvm);
    }

    private void swapTurn()
    {
        if(turn.equals("X"))
        {
            turn = "O";
        }
        else if(turn.equals("O"))
        {
            turn = "X";
        }
    }

    @Override
    public void update(Serializable msg)
    {
        Platform.runLater(() -> {
            if (msg instanceof GameViewersMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/ViewSpectators.fxml"));
                    Parent root = loader.load();
                    ViewSpectatorsController vsc = loader.getController();
                    vsc.passInfo(((GameViewersMessage) msg).getSpectators());
                    Stage stage = new Stage();
                    stage.setTitle("Spectators");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void placeMove()
    {
        int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
        int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
        if(x == 0 && y == 0)
        {
            tile00.setText(turn);
            swapTurn();
        }
        else if(x == 0 && y == 1)
        {
            tile01.setText(turn);
            swapTurn();
        }
        else if(x == 0 && y == 2)
        {
            tile02.setText(turn);
            swapTurn();
        }
        else if(x == 1 && y == 0)
        {
            tile10.setText(turn);
            swapTurn();
        }
        else if(x == 1 && y == 1)
        {
            tile11.setText(turn);
            swapTurn();
        }
        else if(x == 1 && y == 2)
        {
            tile12.setText(turn);
            swapTurn();
        }
        else if(x == 2 && y == 0)
        {
            tile20.setText(turn);
            swapTurn();
        }
        else if(x == 2 && y == 1)
        {
            tile21.setText(turn);
            swapTurn();
        }
        else if(x == 2 && y == 2)
        {
            tile22.setText(turn);
            swapTurn();
        }
    }
}
