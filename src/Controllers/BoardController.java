package Controllers;

import Client.Client;
import DataClasses.Minimax;
import DataClasses.MoveInfo;
import Messages.*;
import TicTacToe.TTT_Move;
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
                    tile.setTextAlignment(TextAlignment.CENTER);
                    tile.setFont(new Font(36));

                    if(playerNumber != 0)
                    {
                        tile.setOnMouseEntered(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (tttBoard[board.getRowIndex(node)][board.getColumnIndex(node)] == 0) {
                                    if(isPlayer1Turn && playerNumber == 1)
                                    {
                                        tile.setText("X");
                                    }
                                    else if(!isPlayer1Turn && playerNumber == 2)
                                    {
                                        tile.setText("O");
                                    }
                                }
                            }
                        });

                        tile.setOnMouseExited(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (tttBoard[board.getRowIndex(node)][board.getColumnIndex(node)] == 0) {
                                    tile.setText("");
                                }
                            }
                        });

                        tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if(((isPlayer1Turn && playerNumber == 1) || (!isPlayer1Turn && playerNumber == 2)) && isInGame && !isPendingMove) {
                                    MoveMessage mm = (MoveMessage) MessageFactory.getMessage("MOV-MSG");
                                    mm.setMovingPlayerId(client.getUser().getId());
                                    mm.setGameId(gameId);
                                    Node node = (Node) mouseEvent.getSource();
                                    mm.setMoveInfo(new MoveInfo(new TTT_Move(playerNumber, GridPane.getRowIndex(node), GridPane.getColumnIndex(node)), LocalDateTime.now()));

                                    isPendingMove = true;
                                    client.update(mm);
                                }
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

                turnLabel.setText(player1Username + "\'s turn!");
            } else if (msg instanceof CreateLobbyMessage) {
                isPlayer1Turn = true;
                this.player1Username = client.getUser().getUsername();
                this.gameId = ((CreateLobbyMessage) msg).getGameLobbyId();
                isInGame = false;

                turnLabel.setText(player1Username + "\'s turn!");
            } else if (msg instanceof ConnectToLobbyMessage) {
                isPlayer1Turn = true;
                isInGame = true;
                this.gameId = ((ConnectToLobbyMessage) msg).getLobbyGameId();
                this.player1Username = ((ConnectToLobbyMessage) msg).getPlayer1();
                this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();

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
            }

            closeButton.setVisible(false /*!isInGame*/);
        });
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
        Platform.runLater(() -> {
            if (msg instanceof ConnectToLobbyMessage) {
                isInGame = true;
                closeButton.setVisible(false);
                this.player2Username = ((ConnectToLobbyMessage) msg).getPlayer2();
            }
            else if (msg instanceof LegalMoveMessage)
            {
                tttBoard[((LegalMoveMessage) msg).getNextMove().getRow()][((LegalMoveMessage) msg).getNextMove().getColumn()] = (isPlayer1Turn ? 1 : -1); // Player's last turn
                isPlayer1Turn = !isPlayer1Turn;
                isPendingMove = false;
                Label tile = null;
                if(((LegalMoveMessage) msg).getNextMove().getRow() == 0 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 0)
                {
                    tile = tile00;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 0 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 1)
                {
                    tile = tile01;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 0 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 2)
                {
                    tile = tile02;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 1 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 0)
                {
                    tile = tile10;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 1 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 1)
                {
                    tile = tile11;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 1 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 2)
                {
                    tile = tile12;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 2 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 0)
                {
                    tile = tile20;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 2 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 1)
                {
                    tile = tile21;
                }
                else if(((LegalMoveMessage) msg).getNextMove().getRow() == 2 && ((LegalMoveMessage) msg).getNextMove().getColumn() == 2)
                {
                    tile = tile22;
                }

                tile.setText((((LegalMoveMessage) msg).getNextMove().getPlayer()) == 1 ? "X" : "O");
                tile.setFont(new Font(36));
                tile.setTextAlignment(TextAlignment.CENTER);
               // board.add(tile, ((LegalMoveMessage) msg).getNextMove().getColumn(), ((LegalMoveMessage) msg).getNextMove().getRow());

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
            else if(msg instanceof GameResultMessage)
            {
                winnerLabel.setText(msg.toString());
                turnLabel.setText("");
                isInGame = false;
                isFinished = true;
                closeButton.setVisible(true);
            }
        });
    }

    public void onCloseClicked()
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

}
