package Controllers;

import Client.Client;
import DataClasses.GameInfo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MenuController implements BaseController, Initializable
{
    private Client client;
    public Button vsPlayerButton;
    public Button vsAIButton;
    public Button accountButton;
    public Button logoutButton;

    public void onVsPlayerClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/VsPlayer.fxml"));
            Parent root = loader.load();
            VsPlayerController vpc = loader.getController();
            vpc.passInfo(client);
            Stage stage = (Stage) vsPlayerButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Tic-Tac-Toe");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onVsAIClicked()
    {
        CreateAIGameMessage cam = (CreateAIGameMessage) MessageFactory.getMessage("CAI-MSG");
        cam.setPlayer1Id(client.getUser().getId());
        cam.setStartTime(LocalDateTime.now());
        client.update(cam);
    }

    public void onAccountClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
            ac.passInfo(client);
            Stage stage = (Stage) accountButton.getScene().getWindow();
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

    public void onLogoutClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
            Parent root = loader.load();
            LoginController lc = loader.getController();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg)
    {
        if(msg instanceof CreateAIGameMessage)
        {
            client.getGames().add(new GameInfo("AI", ((CreateAIGameMessage) msg).getStartTime(), ((CreateAIGameMessage) msg).getGameId()));

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                Parent root = loader.load();
                BoardController bc = loader.getController();
                bc.passInfo(client, ((CreateAIGameMessage) msg).getGameId());
                Stage stage = (Stage) vsAIButton.getScene().getWindow();
                stage.close();
                stage.setTitle("Tic-Tac-Toe (Vs. AI)");
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
