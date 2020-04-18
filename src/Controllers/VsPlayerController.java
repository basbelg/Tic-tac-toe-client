package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class VsPlayerController implements BaseController, Initializable
{
    //CREATELOBBY SENDS OUT A CREATE LOBBY MESSAGE TO SERVER
    //ACTIVE LOBBIES LIST SHOULD BE A LOCAL FIELD CLIENT SIDE (IN CLIENT CLASS)
    //private Client client;
    public Button createLobbyButton;
    public Button backButton;
    public ListView activeGamesList;

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Menu.fxml"));
            Parent root = loader.load();
            MenuController mc = loader.getController();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Menu");
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
    public void update(Serializable msg) {

    }
}
