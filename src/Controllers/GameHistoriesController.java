package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class GameHistoriesController implements BaseController, Initializable
{
    public ListView gameList;
    private int doubleClicked = 0;

    public void onGameListClicked()
    {
        doubleClicked++;

        if(doubleClicked % 2 == 0)
        {
            //pass in move history to be loaded into gridpane
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {

    }
}
