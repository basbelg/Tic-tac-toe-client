package Controllers;

import Client.Client;
import Messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements BaseController, Initializable
{
    private Client client = new Client(this);
    public Button signInButton;
    public Button newUserButton;
    public TextField enterUsername;
    public PasswordField enterPassword;
    public Label invalidLabel;

    public void onUsernameChanged()
    {
        if(!enterPassword.getText().equals("") && !enterUsername.getText().equals(""))
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
        if(!enterUsername.getText().equals("") && !enterPassword.getText().equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }

    public void onSignInClicked()
    {
        LoginMessage lgm = (LoginMessage) MessageFactory.getMessage("LOG-MSG");
        lgm.setUsername(enterUsername.getText());
        lgm.setPassword(enterPassword.getText());
        signInButton.setDisable(true);
        newUserButton.setDisable(true);
        client.update(lgm);
    }

    public void onNewUserClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
            Parent root = loader.load();
            RegisterController rc = loader.getController();
            rc.confirmButton.setText("Register");
            rc.passInfo(client);
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

    @Override
    public void update(Serializable msg)
    {
        Platform.runLater(() -> {
            if (msg instanceof LoginSuccessfulMessage) {
                client.setUser(((LoginSuccessfulMessage) msg).getUser());

                AllActiveGamesMessage aagm = (AllActiveGamesMessage) MessageFactory.getMessage("AAG-MSG");
                aagm.setSenderId(client.getUser().getId());
                client.update(aagm);

            } else if (msg instanceof LoginFailedMessage) {
                newUserButton.setDisable(false);
                signInButton.setDisable(false);
                invalidLabel.setText(msg.toString());
            }
            else if(msg instanceof AllActiveGamesMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Menu.fxml"));
                    Parent root = loader.load();
                    MenuController mc = loader.getController();
                    mc.passInfo(client);
                    Stage stage = (Stage) newUserButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Menu");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
