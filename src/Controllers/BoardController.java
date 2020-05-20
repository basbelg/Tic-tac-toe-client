package Controllers;

import Client.Client;
import DataClasses.Minimax;
import DataClasses.MoveInfo;
import Messages.*;
import TicTacToe.TTT_Move;
import com.mysql.cj.protocol.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public Label tile00;
    public Label tile01;
    public Label tile02;
    public Label tile10;
    public Label tile11;
    public Label tile12;
    public Label tile20;
    public Label tile21;
    public Label tile22;
    private Client client;
    private String gameId;
    private boolean isPlayer1Turn = true;
    private boolean isInGame;
    private boolean isFinished = false;
    private boolean isPendingMove;
    private int playerNumber;   // 0 if the client is a spectator
    private String player1Username;
    private String player2Username;
    private Minimax aiPlayer = new Minimax();
    private int[][] tttBoard = new int[3][3];


    public void passInfo(Client client, Serializable msg, int playerNumber)
    {
        this.playerNumber = playerNumber;
        this.client = client;
        this.isPendingMove = false;
        client.setController(this);



        Platform.runLater(() -> {

                List<Node> boardTiles = new ArrayList<>();
                boardTiles.add(tile00);
                boardTiles.add(tile01);
                boardTiles.add(tile02);
                boardTiles.add(tile10);
                boardTiles.add(tile11);
                boardTiles.add(tile12);
                boardTiles.add(tile20);
                boardTiles.add(tile21);
                boardTiles.add(tile22);

                for(Node node : boardTiles)
                {
                    Label tile = (Label) node;
                    //tile.setTextAlignment(TextAlignment.CENTER);
                    //tile.setFont(new Font(36));

                    if(playerNumber != 0)
                    {
                        tile.setOnMouseEntered(mouseEvent -> {
                            if (tttBoard[board.getRowIndex(node)][board.getColumnIndex(node)] == 0 && !isFinished) {
                                if(isPlayer1Turn && playerNumber == 1)
                                {
                                    tile.setText("X");
                                }
                                else if(!isPlayer1Turn && playerNumber == 2)
                                {
                                    tile.setText("O");
                                }
                            }
                        });

                        tile.setOnMouseExited(mouseEvent -> {
                            if (tttBoard[board.getRowIndex(node)][board.getColumnIndex(node)] == 0 && !isFinished) {
                                tile.setText("");
                            }
                        });

                        tile.setOnMouseClicked(mouseEvent -> {
                            if (((isPlayer1Turn && playerNumber == 1) || (!isPlayer1Turn && playerNumber == 2)) && isInGame && !isPendingMove) {
                                MoveMessage mm = (MoveMessage) MessageFactory.getMessage("MOV-MSG");
                                mm.setMovingPlayerId(client.getUser().getId());
                                mm.setGameId(gameId);
                                Node node1 = (Node) mouseEvent.getSource();
                                mm.setMoveInfo(new MoveInfo(new TTT_Move(playerNumber, GridPane.getRowIndex(node1), GridPane.getColumnIndex(node1)), LocalDateTime.now()));

                                isPendingMove = true;
                                client.update(mm);
                            }
                        });
                    }

                }


            if (msg instanceof CreateAIGameMessage) {
                isPlayer1Turn = true;
                this.player1Username = client.getUser().getUsername();
                this.player2Username = "AI Player";
                this.gameId = ((CreateAIGameMessage) msg).getGameLobbyId();
                isInGame = true;
                closeButton.setText("Concede");
                turnLabel.setText(player1Username + "\'s turn!");
            } else if (msg instanceof CreateLobbyMessage) {
                isPlayer1Turn = true;
                this.player1Username = client.getUser().getUsername();
                this.gameId = ((CreateLobbyMessage) msg).getGameLobbyId();
                isInGame = false;
                closeButton.setText("Leave Game");
                winnerLabel.setText("Waiting for player...");
                turnLabel.setText("");
            } else if (msg instanceof ConnectToLobbyMessage) {
                isPlayer1Turn = true;
                isInGame = true;
                this.gameId = ((ConnectToLobbyMessage) msg).getLobbyGameId();
                this.player1Username = ((ConnectToLobbyMessage) msg).getPlayer1();
                this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();
                closeButton.setText("Concede");
                winnerLabel.setText(player1Username + " VS. " + player2Username);
                turnLabel.setText(player1Username + "\'s turn!");
            } else if (msg instanceof SpectateMessage) {
                isInGame = true;
                this.gameId = ((SpectateMessage) msg).getGameId();
                this.player1Username = ((SpectateMessage) msg).getPlayer1Username();
                this.player2Username = ((SpectateMessage) msg).getPlayer2Username();
                int[][] spectatorBoard = ((SpectateMessage) msg).getGameBoard();
                int turn = 1;

                for (int i = 0; i < spectatorBoard.length; ++i) {
                    for (int j = 0; j < spectatorBoard[i].length; ++j) {
                        if (spectatorBoard[i][j] == 1) {
                            placeMove(i, j, "X");
                            ++turn;
                        }
                        else if (spectatorBoard[i][j] == -1) {
                            placeMove(i, j, "O");
                            ++turn;
                        }
                    }
                }

                isPlayer1Turn = (turn % 2) == 1;
                turnLabel.setText(isPlayer1Turn ? player1Username + "\'s turn!" : player2Username + "\'s turn!");
                closeButton.setText("Stop Spectating");
            }
        });
    }

    public void onSpectatorsClicked()
    {
        GameViewersMessage gvm = (GameViewersMessage) MessageFactory.getMessage("GVW-MSG");
        gvm.setUserId(client.getUser().getId());
        gvm.setGameId(gameId);
        gvm.setGameActive(true);
        client.update(gvm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg)
    {
        Platform.runLater(() -> {
            if (msg instanceof ConnectToLobbyMessage) {
                isInGame = true;
                this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();
                closeButton.setText("Concede");
                turnLabel.setText(player2Username + " has connected. \n" + player1Username + "\'s turn!");
                winnerLabel.setText(player1Username + " VS. " + player2Username);
            }
            else if (msg instanceof LegalMoveMessage)
            {
                tttBoard[((LegalMoveMessage) msg).getNextMove().getRow()][((LegalMoveMessage) msg).getNextMove().getColumn()] = (isPlayer1Turn ? 1 : -1); // Player's last turn
                isPlayer1Turn = !isPlayer1Turn;
                isPendingMove = false;

                placeMove(((LegalMoveMessage) msg).getNextMove().getRow(), ((LegalMoveMessage) msg).getNextMove().getColumn(), (((LegalMoveMessage) msg).getNextMove().getPlayer()) == 1 ? "X" : "O");

                errorLabel.setText("");

                if (playerNumber != 0 && !isFinished && aiPlayer.getEvaluator().evaluate(tttBoard) == null)
                {
                    turnLabel.setText((isPlayer1Turn ? player1Username + "\'s turn!" : player2Username + "\'s turn!"));


                    if(player2Username.equals("AI Player") && !isPlayer1Turn) {
                        int pos = aiPlayer.generateTurn(tttBoard);
                        MoveMessage mm = (MoveMessage) MessageFactory.getMessage("MOV-MSG");
                        mm.setMovingPlayerId(1);
                        mm.setGameId(gameId);
                        mm.setMoveInfo(new MoveInfo(new TTT_Move(2, pos/3, pos%3), LocalDateTime.now()));
                        client.update(mm);
                    }
                }

            } else if (msg instanceof IllegalMoveMessage) {
                isPendingMove = false;
                errorLabel.setText(msg.toString());
            } else if (msg instanceof GameViewersMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/ViewSpectators.fxml"));
                    Parent root = loader.load();
                    ViewSpectatorsController vsc = loader.getController();
                    vsc.passInfo(((GameViewersMessage) msg).getSpectators());
                    Stage stage = new Stage();
                    stage.setTitle("Spectators");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(msg instanceof InactiveGameMessage)
            {
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
            else if(msg instanceof StopSpectatingMessage)
            {
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
            else if(msg instanceof GameResultMessage)
            {
                winnerLabel.setText(msg.toString());
                turnLabel.setText("");
                isInGame = false;
                isFinished = true;
                closeButton.setText("Leave Game");
            }
            finishedWaitingForServer();
        });
    }

    public void onCloseClicked()
    {
        waitingForServer();
        if(isFinished)
        {
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
        else if(!isInGame)
        {
            InactiveGameMessage im = (InactiveGameMessage) MessageFactory.getMessage("IAG-MSG");
            im.setFinishedGameId(gameId);
            client.update(im);
        }
        else if(playerNumber != 0)
        {
            ConcedeMessage cm = (ConcedeMessage) MessageFactory.getMessage("CNC-MSG");
            cm.setGameId(gameId);
            client.update(cm);
        }
        else if(playerNumber == 0)
        {
            StopSpectatingMessage ssm = (StopSpectatingMessage) MessageFactory.getMessage("SSP-MSG");
            ssm.setGameId(gameId);
            ssm.setPlayerId(client.getUser().getId());
            client.update(ssm);
        }
    }

    private void placeMove(int row, int col, String turn)
    {
        if(row == 0 && col == 0)
        {
            tile00.setText(turn);
        }
        else if(row == 0 && col == 1)
        {
            tile01.setText(turn);
        }
        else if(row == 0 && col == 2)
        {
            tile02.setText(turn);
        }
        else if(row == 1 && col == 0)
        {
            tile10.setText(turn);
        }
        else if(row == 1 && col == 1)
        {
            tile11.setText(turn);
        }
        else if(row == 1 && col == 2)
        {
            tile12.setText(turn);
        }
        else if(row == 2 && col == 0)
        {
            tile20.setText(turn);
        }
        else if(row == 2 && col == 1)
        {
            tile21.setText(turn);
        }
        else if(row == 2 && col == 2)
        {
            tile22.setText(turn);
        }
    }

    private void waitingForServer()
    {
        closeButton.setDisable(true);
        spectatorsButton.setDisable(true);
    }

    private void finishedWaitingForServer()
    {
        closeButton.setDisable(false);
        spectatorsButton.setDisable(false);
    }

}
