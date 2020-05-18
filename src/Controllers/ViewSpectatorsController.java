package Controllers;

import DataClasses.Spectator;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSpectatorsController implements Initializable
{
    public ListView spectatorsList;
    public Button closeButton;

    public void onCloseClicked()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void passInfo(List<Spectator> spectators)
    {
        Platform.runLater(() -> {
            Label label = null;
            if(!spectators.isEmpty())
            {
                for (Spectator s : spectators) {
                    label = new Label("User: " + s.getUsername());
                    spectatorsList.getItems().add(label);
                }
            }
            else
            {
                label = new Label("No spectators for this match");
                spectatorsList.getItems().add(label);
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
