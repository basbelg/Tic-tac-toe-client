package Controllers;

import Client.Client;
import DataClasses.MoveInfo;
import TicTacToe.TTT_Move;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class BoardController implements BaseController, Initializable
{
    //SENDING OUT MOVEMESSAGES, RECEIVING THROUGH UPDATE() MOVESUCCESSFUL AND MOVEFAILED
    public Button spectatorsButton;
    public GridPane board;
    private Client client;
    private String gameId;
    private boolean isPlayerTurn;


    public void passInfo(Client client, String gameId)
    {
        this.client = client;
        client.setController(this);
        this.gameId = gameId;
    }

    public void onBoardClicked()
    {
        if(isPlayerTurn)
        {
            MoveMessage mm = (MoveMessage) MessageFactory.getMessage();
            mm.setMovingPlayerId(client.getUser().getId());
            mm.setGameId(gameId);
            mm.setMoveInfo(new MoveInfo(new TTT_Move(1, row, column), LocalDateTime.now()));
            client.update(mm);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {

    }
}
