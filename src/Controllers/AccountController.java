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

public class AccountController implements BaseController, Initializable
{
    //GAME HISTORY WINDOW, DELETE ACCOUNT WINDOW
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
        //DO PLATFORM.RUNLATER(() ->
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
            Parent root = loader.load();
            RegisterController rc = loader.getController();
            Stage stage = (Stage) modAccountButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Modify Account");
            rc.confirmButton.setText("Confirm");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onGameHistoriesClicked()
    {
        try
        {
            //pass in a list of all previous games
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/GameHistories.fxml"));
            Parent root = loader.load();
            GameHistoriesController ghc = loader.getController();
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
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Stats.fxml"));
            Parent root = loader.load();
            StatsController sc = loader.getController();
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

    public void onDeleteAccountClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/DeleteAccount.fxml"));
            Parent root = loader.load();
            DeleteAccountController dac = loader.getController();
            Stage stage = new Stage();
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
    public void update(Serializable msg) {

    }
}
