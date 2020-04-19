package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements BaseController, Initializable
{
    public Button confirmButton;
    public Button cancelButton;
    private int userId;

    public void onCancelClicked()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onConfirmClicked()
    {
        DeactivateAccountMessage dam = (DeactivateAccountMessage) MessageFactory.getMessage();
        dam.setUserId(userId);

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void passInfo(int userId)
    {
        this.userId = userId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {

    }
}
