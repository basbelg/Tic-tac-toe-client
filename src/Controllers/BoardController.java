package Controllers;

import Client.Client;
import DataClasses.MoveInfo;
import TicTacToe.TTT_Move;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class BoardController implements BaseController, Initializable
{
    //SENDING OUT MOVEMESSAGES, RECEIVING THROUGH UPDATE() MOVESUCCESSFUL AND MOVEFAILED
    public Button spectatorsButton;
    public GridPane board;
    public Label outLabel;
    private Client client;
    private String gameId;
    private int moveTurn = 1;
    private boolean isTurn;
    private boolean isSpectator = false;
    private boolean gameStarted;
    private int playerNumber;
    private String player1Username;
    private String player2Username;


    public void passInfo(Client client, Serializable msg, int playerNumber)
    {
        this.playerNumber = playerNumber;
        this.client = client;
        client.setController(this);
        isTurn = (playerNumber == 1);
        if(msg instanceof CreateAIGameMessage)
        {
            this.gameId = ((CreateAIGameMessage) msg).getGameLobbyId();
            gameStarted = true;
        }
        else if(msg instanceof CreateLobbyMessage)
        {
            this.gameId = ((CreateLobbyMessage) msg).getGameLobbyId();
            gameStarted = false;
        }
        else if(msg instanceof ConnectToLobbyMessage)
        {
            gameStarted = true;
            this.gameId = ((ConnectToLobbyMessage) msg).getLobbyGameId();
        }
        else if(msg instanceof SpectateMessage)
        {
            this.gameId = ((SpectateMessage) msg).getLobbyGameId();
            isSpectator = true;
        }
    }

    public void onBoardClicked()
    {
        if(isTurn && !isSpectator)
        {
            MoveMessage mm = (MoveMessage) MessageFactory.getMessage();
            mm.setMovingPlayerId(client.getUser().getId());
            mm.setGameId(gameId);
            mm.setMoveInfo(new MoveInfo(new TTT_Move(playerNumber, row, column), LocalDateTime.now()));
            client.update(mm);
        }

    }

    public void onSpectatorsClicked()
    {
        GameViewersMessage gvm = (GameViewersMessage) MessageFactory.getMessage("GVW-MSG");
        gvm.setUserId(client.getUser().getId());
        gvm.setGameId(gameId);
        client.update(gvm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg)
    {
        if(msg instanceof ConnectToLobbyMessage)
        {
            gameStarted = true;
        }
        if(gameStarted)
        {
            if(msg instanceof LegalMoveMessage)
            {
                isTurn = ((LegalMoveMessage) msg).getNextMove().getPlayer() != playerNumber;
                Label tile = new Label(playerNumber == 1 ? "X" : "O");
                tile.setFont(new Font(36));
                board.add(new Label(playerNumber == 1 ? "X" : "O"), ((LegalMoveMessage) msg).getMove().getColumn(), ((LegalMoveMessage) msg).getMove().getColumn());
            }
            else if(msg instanceof IllegalMoveMessage)
            {
                outLabel.setText(((IllegalMoveMessage) msg).toString());
            }
            else if(msg instanceof GameViewersMessage)
            {
                try
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/ViewSpectators.fxml"));
                    Parent root = loader.load();
                    ViewSpectatorsController vsc = loader.getController();
                    vsc.passInfo(((GameViewersMessage) msg).getSpectators());
                    Stage stage = new Stage();
                    stage.setTitle("Spectators");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

}
