package Controllers;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
