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
    public Label winPercentLabel;

    public void onExitClicked()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void passInfo(StatsMessage stats)
    {
        Platform.runLater(() -> {
            int gamesPlayed = stats.getLosses() + stats.getTies() + stats.getWins();
            StringBuffer winPercent = new StringBuffer();

            wltLabel.setText(stats.getWins() + "/" + stats.getLosses() + "/" + stats.getTies());
            gamesPlayedLabel.setText(String.valueOf(gamesPlayed));
            if(gamesPlayed <= 0)
            {
                winPercent.append("0%");
            }
            else
            {
               winPercent.append((int)(((double) stats.getWins()) / gamesPlayed * 100) + "%");
            }
            winPercentLabel.setText(winPercent.toString());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
