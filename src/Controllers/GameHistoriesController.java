package Controllers;

import Client.Client;
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

public class GameHistoriesController implements BaseController, Initializable
{
    private Client client;
    public ListView gameList;
    public Button backButton;
    public Button viewMoveHistoryButton;

    public void onViewMoveHistoryClicked()
    {
        //send in move history of game selected
        GameLogMessage glm = (GameLogMessage) MessageFactory.getMessage("GLG-MSG");
        glm.setUserId(client.getUser().getId());
        glm.setGameId(client.getGameIds().get(gameList.getSelectionModel().getSelectedIndex()));
        client.update(glm);
    }

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
            ac.passInfo(client);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Account");
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
        if(!(GameLogMessage) msg.getMoveHistory().equals(null))
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/MoveHistory.fxml"));
                Parent root = loader.load();
                MoveHistoryController mhc = loader.getController();
                mhc.passInfo((GameLogMessage) msg);
                Stage stage = new Stage();
                stage.setTitle("Move History");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);
    }
}
