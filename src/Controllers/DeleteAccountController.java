package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements BaseController, Initializable
{
    public Button confirmButton;
    public Button cancelButton;

    public void onCancelClicked()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {

    }
}
