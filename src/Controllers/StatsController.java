package Controllers;

import Messages.StatsMessage;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class StatsController implements Initializable
{
    public Label wltLabel;
    public Label gamesPlayedLabel;
    public Button exitButton;

    public void onExitClicked()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void passInfo(StatsMessage stats)
    {
        Platform.runLater(() -> {
            wltLabel.setText(stats.getWins() + "/" + stats.getLosses() + "/" + stats.getTies());
            gamesPlayedLabel.setText(((Integer) (stats.getWins() + stats.getLosses() + stats.getTies())).toString());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
