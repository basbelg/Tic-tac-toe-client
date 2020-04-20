package Controllers;

import Client.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MoveHistoryController implements BaseController, Initializable
{
    public Label moveNumLabel;
    public Button nextButton;
    public Button previousButton;
    public Button cancelButton;
    public GridPane board;
    public Label timeLabel;
    public Label playerLabel;
    private GameLogMessage glm;
    private int moveCounter = 0;
    private String turn = "X";
    private Client client;

    public void onCancelClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/GameHistories.fxml"));
            Parent root = loader.load();
            GameHistoriesController ghc = loader.getController();
            ghc.passInfo(client);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
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

        Platform.runLater(() -> {
            int x = glm.getMoveHistory().get(0).getNextMove().getRow();
            int y = glm.getMoveHistory().get(0).getNextMove().getColumn();
            board.add(new Label(getTurn()), y, x);

            moveNumLabel.setText("1/" + glm.getMoveHistory().size().toString());
            timeLabel.setText(glm.getMoveHistory().get(0).getTimeMade().toString());
            playerLabel.setText(glm.getPlayer1Username() + "\'s turn!");
            previousButton.setDisable(true);
        });
    }

    public void onNextClicked()
    {
        Platform.runLater(() -> {
            moveCounter++;

            int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
            int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
            board.add(new Label(getTurn()), y, x);
            moveNumLabel.setText(((Integer) (moveCounter + 1)).toString() + "/" + glm.getMoveHistory().size().toString());
            timeLabel.setText(glm.getMoveHistory().get(moveCounter).getTimeMade().toString());
            playerLabel.setText(playerLabel.getText().equals(glm.getPlayer1Username()) ? (glm.getPlayer2Username() + "\'s turn!") : (glm.getPlayer1Username() + "\'s turn!"));

            if (moveCounter >= (glm.getMoveHistory().size() - 1)) {
                nextButton.setDisable(true);
            } else if (previousButton.isDisable()) {
                previousButton.setDisable(false);
            }
        });
    }

    public void onPreviousClicked()
    {
        Platform.runLater(() -> {
            int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
            int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
            moveCounter--;

            board.add(new Label(""), y, x);
            moveNumLabel.setText(moveCounter.toString() + "/" + glm.getMoveHistory.size().toString());
            timeLabel.setText(glm.getMoveHistory().get(moveCounter).getTimeMade().toString());
            playerLabel.setText(playerLabel.getText().equals(glm.getPlayer1Username()) ? glm.getPlayer2Username() : glm.getPlayer1Username());

            if (moveCounter <= 0) {
                previousButton.setDisable(true);
            } else if (nextButton.isDisable()) {
                nextButton.setDisable(false);
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

    private String getTurn()
    {
        if(turn.equals("X"))
        {
            turn = "O";
            return "X";
        }
        else if(turn.equals("O"))
        {
            turn = "X";
            return "O";
        }

        return "ERROR";
    }

    @Override
    public void update(Serializable msg)
    {
        if(msg instanceof GameViewersMessage)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/ViewSpectators.fxml"));
                Parent root = loader.load();
                ViewSpectatorsController vsc = loader.getController();
                vsc.passInfo(((GameViewersMessage) msg).getSpectators());
                Stage stage = new Stage();
                stage.setTitle("Spectators");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
