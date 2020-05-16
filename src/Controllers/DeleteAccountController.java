package Controllers;

import Client.Client;
import Messages.DeactivateAccountMessage;
import Messages.MessageFactory;
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

public class DeleteAccountController implements BaseController, Initializable {
    public Button confirmButton;
    public Button cancelButton;
    private Client client;

    public void onCancelClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
            ac.passInfo(client);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Account");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConfirmClicked() {
        DeactivateAccountMessage dam = (DeactivateAccountMessage) MessageFactory.getMessage("DAC-MSG");
        dam.setUserId(client.getUser().getId());
        client.update(dam);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
            client.terminateThread();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void passInfo(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void update(Serializable msg) {

    }

}