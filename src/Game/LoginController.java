package Game;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    public Button signInButton;
    public Button newUserButton;
    public TextField enterUsername;
    public TextField enterPassword;

    public void onUsernameChanged()
    {
        if(!enterPassword.getText().equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }

    public void onPasswordChanged()
    {
        if(!enterUsername.getText().equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }

    public void onSignInClicked()
    {}

    public void onNewUserClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
            Parent root = loader.load();
            RegisterController rc = loader.getController();
            Stage stage = (Stage) newUserButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Register");
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
}
