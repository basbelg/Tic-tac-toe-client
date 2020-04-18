package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MoveHistoryController implements Initializable
{
    public Label moveNumLabel;
    public Button nextButton;
    public Button previousButton;
    public Button cancelButton;
    public GridPane board;

    public void onCancelClicked()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
