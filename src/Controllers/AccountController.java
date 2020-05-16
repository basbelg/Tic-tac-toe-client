package Controllers;

import Client.Client;
import Messages.GamesPlayedMessage;
import Messages.MessageFactory;
import Messages.StatsMessage;
import javafx.application.Platform;
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

public class AccountController implements BaseController, Initializable
{
    //GAME HISTORY WINDOW, DELETE ACCOUNT WINDOW
    private Client client;
    public Button modAccountButton;
    public Button gameHistoryButton;
    public Button backButton;
    public Button statsButton;
    public Button deleteAccountButton;

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Menu.fxml"));
            Parent root = loader.load();
            MenuController mc = loader.getController();
            mc.passInfo(client);
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

    public void onModAccountClicked()
    {
        //Send off data to the Client class to be sent to the Server through a thread
        //IF ACCOUNTFAILEDMESSAGE RETURNS, APPEND TO_STRING TO LABEL
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
                Parent root = loader.load();
                RegisterController rc = loader.getController();
                rc.confirmButton.setText("Confirm");
                rc.passInfo(client);
                Stage stage = (Stage) modAccountButton.getScene().getWindow();
                stage.close();
                stage.setTitle("Modify Account");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onGameHistoriesClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/GameHistories.fxml"));
            Parent root = loader.load();
            GameHistoriesController ghc = loader.getController();
            ghc.passInfo(client);
            Stage stage = (Stage) gameHistoryButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Game Histories");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onStatsClicked()
    {
        //SEND W/L/T RATIO OVER TO STATS WINDOW TO BE DISPLAYED (Controller object)
        StatsMessage sm = (StatsMessage) MessageFactory.getMessage("STS-MSG");
        waitingForServer();
        client.update(sm);

    }

    public void onDeleteAccountClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/DeleteAccount.fxml"));
            Parent root = loader.load();
            DeleteAccountController dac = loader.getController();
            dac.passInfo(client);
            Stage stage = (Stage) deleteAccountButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Delete Account");
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
            finishedWaitingForServer();
            if(msg instanceof StatsMessage)
            {
                try
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Stats.fxml"));
                    Parent root = loader.load();
                    StatsController sc = loader.getController();
                    sc.passInfo((StatsMessage) msg);
                    Stage stage = new Stage();
                    stage.setTitle("Statistics");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);
    }

    private void waitingForServer()
    {
        backButton.setDisable(true);
        gameHistoryButton.setDisable(true);
        modAccountButton.setDisable(true);
        deleteAccountButton.setDisable(true);
    }

    private void finishedWaitingForServer()
    {
        backButton.setDisable(false);
        gameHistoryButton.setDisable(false);
        modAccountButton.setDisable(false);
        deleteAccountButton.setDisable(false);
    }
}
