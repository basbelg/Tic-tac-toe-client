package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements BaseController, Initializable
{
    //SENDING OUT MOVEMESSAGES, RECEIVING THROUGH UPDATE() MOVESUCCESSFUL AND MOVEFAILED
    public Button spectatorsButton;
    public GridPane board;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {

    }
}
