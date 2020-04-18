package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameHistoriesController implements Initializable
{
    public ListView gameList;
    public Button backButton;
    public Button viewMoveHistoryButton;

    public void onViewMoveHistoryClicked()
    {
        //send in move history of game selected
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/MoveHistory.fxml"));
            Parent root = loader.load();
            MoveHistoryController mhc = loader.getController();
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

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
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
}
