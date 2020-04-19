package Controllers;

import GameInterfaces.Move;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MoveHistoryController implements Initializable
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

    public void onCancelClicked()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void passInfo(GameLogMessage glm)
    {
        this.glm = glm;

        int x = glm.getMoveHistory().get(0).getNextMove().getRow();
        int y = glm.getMoveHistory().get(0).getNextMove().getColumn();
        board.add(new Label(getTurn()), y, x);

        moveNumLabel.setText("1/" + glm.getMoveHistory.size().toString());
        timeLabel.setText(glm.getMoveHistory.get(0).getTimeMade().toString());
        playerLabel.setText(glm.getPlayer1Username());
    }

    public void onNextClicked()
    {
        moveCounter++;

        int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
        int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
        board.add(new Label(getTurn()), y, x);
        moveNumLabel.setText(moveCounter.toString() + "/" + glm.getMoveHistory.size().toString());
        timeLabel.setText(glm.getMoveHistory.get(moveCounter).getTimeMade().toString());
        playerLabel.setText(playerLabel.getText().equals(glm.getPlayer1Username()) ? glm.getPlayer2Username() : glm.getPlayer1Username());

    }

    public void onPreviousClicked()
    {
        moveCounter--;

        int x = glm.getMoveHistory().get(moveCounter).getNextMove().getRow();
        int y = glm.getMoveHistory().get(moveCounter).getNextMove().getColumn();
        board.add(new Label(""), y, x);
        moveNumLabel.setText(moveCounter.toString() + "/" + glm.getMoveHistory.size().toString());
        timeLabel.setText(glm.getMoveHistory.get(moveCounter).getTimeMade().toString());
        playerLabel.setText(playerLabel.getText().equals(glm.getPlayer1Username()) ? glm.getPlayer2Username() : glm.getPlayer1Username());
    }

    public void onSpectatorsClicked()
    {

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
    }
}
