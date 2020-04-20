package Controllers;

import Client.Client;
import DataClasses.MoveInfo;
import TicTacToe.TTT_Move;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    public Label errorLabel;
    public Label winnerLabel;
    public Label turnLabel;
    public Button closeButton;
    private Client client;
    private String gameId;
    private boolean isPlayer1Turn;
    private boolean isInGame;
    private int playerNumber;   // 0 if the client is a spectator
    private String player1Username;
    private String player2Username;


    public void passInfo(Client client, Serializable msg, int playerNumber)
    {
        this.playerNumber = playerNumber;
        this.client = client;
        client.setController(this);

        if(msg instanceof CreateAIGameMessage)
        {
            isPlayer1Turn = true;
            this.player1Username = client.getUser().getUsername();
            this.player2Username = "AI Player";
            this.gameId = ((CreateAIGameMessage) msg).getGameLobbyId();
            isInGame = true;

            turnLabel.setText(player1Username + "\'s turn!");
        }
        else if(msg instanceof CreateLobbyMessage)
        {
            isPlayer1Turn = true;
            this.player1Username = client.getUser().getUsername();
            this.gameId = ((CreateLobbyMessage) msg).getGameLobbyId();
            isInGame = false;

            turnLabel.setText(player1Username + "\'s turn!");
        }
        else if(msg instanceof ConnectToLobbyMessage)
        {
            isPlayer1Turn = true;
            isInGame = true;
            this.gameId = ((ConnectToLobbyMessage) msg).getLobbyGameId();
            this.player1Username = ((ConnectToLobbyMessage) msg).getPlayer1();
            this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();

            turnLabel.setText(player1Username + "\'s turn!");
        }
        else if(msg instanceof SpectateMessage)
        {
            isInGame = true;
            this.gameId = ((SpectateMessage) msg).getLobbyGameId();
            int[][] spectatorBoard = ((SpectateMessage) msg).getGameBoard();
            int turn = 1;

            for(int i = 0; i < spectatorBoard.length; ++i)
            {
                for(int j = 0; j < spectatorBoard[i].length; ++j)
                {
                    if(spectatorBoard[i][j] == 1)
                    {
                        Label tile = new Label("X");
                        tile.setFont(new Font(36));
                        board.add(tile, j, i);
                        ++turn;
                    }
                    else if(spectatorBoard[i][j] == -1)
                    {
                        Label tile = new Label("O");
                        tile.setFont(new Font(36));
                        board.add(tile, j, i);
                        ++turn;
                    }
                }
            }

            isPlayer1Turn = (turn % 2) == 1;
            turnLabel.setText(isPlayer1Turn ? player1Username + "\'s turn!" : player2Username + "\'s turn!");
        }

        closeButton.setVisible(!isInGame);
    }

    public void onBoardClicked()
    {
        if(((isPlayer1Turn && playerNumber == 1) || (!isPlayer1Turn && playerNumber == 2)) && isInGame)
        {
            MoveMessage mm = (MoveMessage) MessageFactory.getMessage();
            mm.setMovingPlayerId(client.getUser().getId());
            mm.setGameId(gameId);

            board.setOnMouseClicked(mouseEvent -> {
                Node node = (Node) mouseEvent.getSource();
                mm.setMoveInfo(new MoveInfo(new TTT_Move(playerNumber, GridPane.getRowIndex(node), GridPane.getColumnIndex(node)), LocalDateTime.now()));
            });
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
            isInGame = true;
            closeButton.setVisible(false);
            this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();
        }
        else if(msg instanceof LegalMoveMessage)
        {
            isPlayer1Turn = !isPlayer1Turn;
            Label tile = new Label((((LegalMoveMessage) msg).getMove().getPlayer()) == 1 ? "X" : "O");
            tile.setFont(new Font(36));
            board.add(tile, ((LegalMoveMessage) msg).getMove().getColumn(), ((LegalMoveMessage) msg).getMove().getRow());

            turnLabel.setText((isPlayer1Turn ? player1Username + "\'s turn!" : player2Username + "\'s turn!"));
        }
        else if(msg instanceof IllegalMoveMessage)
        {
            errorLabel.setText(((IllegalMoveMessage) msg).toString());
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
        else if(msg instanceof GameResultMessage)
        {
            winnerLabel.setText(((GameResultMessage) msg).toString());
            isInGame = false;
            closeButton.setVisible(true);
        }
    }

    public void onCloseClicked()
    {
        InactiveGameMessage igm = (InactiveGameMessage) MessageFactory.getMessage("IAG-MSG");
        igm.setFinishedGameId(gameId);
        client.update(igm);

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Menu.fxml"));
            Parent root = loader.load();
            MenuController mc = loader.getController();
            mc.passInfo(client);
            Stage stage = (Stage) closeButton.getScene().getWindow();
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
}
