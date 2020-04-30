package Controllers;

import Client.Client;
import DataClasses.LobbyInfo;
import Messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class VsPlayerController implements BaseController, Initializable
{
    //CREATELOBBY SENDS OUT A CREATE LOBBY MESSAGE TO SERVER
    //ACTIVE LOBBIES LIST SHOULD BE A LOCAL FIELD CLIENT SIDE (IN CLIENT CLASS)
    private Client client;
    public Button createLobbyButton;
    public Button backButton;
    public ListView activeGamesList;
    public Button joinLobbyButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg) {
        Platform.runLater(() -> {
            if (msg instanceof ConnectToLobbyMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                    Parent root = loader.load();
                    BoardController bc = loader.getController();
                    bc.passInfo(client, msg, 2);
                    Stage stage = (Stage) joinLobbyButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Tic-Tac-Toe (Vs. Player)");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (msg instanceof SpectateMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                    Parent root = loader.load();
                    BoardController bc = loader.getController();
                    bc.passInfo(client, msg, 0);
                    Stage stage = (Stage) joinLobbyButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Tic-Tac-Toe (Vs. Player)");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (msg instanceof InactiveGameMessage) {
                activeGamesList.getItems().clear();
                for (LobbyInfo l : client.getActiveGames()) {
                    activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
                }
            } else if (msg instanceof NewAILobbyMessage) {
                activeGamesList.getItems().clear();
                for (LobbyInfo l : client.getActiveGames()) {
                    activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
                }
            } else if (msg instanceof NewLobbyMessage) {
                activeGamesList.getItems().clear();
                for (LobbyInfo l : client.getActiveGames()) {
                    activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
                }
            } else if (msg instanceof FullLobbyMessage) {
                activeGamesList.getItems().clear();
                for (LobbyInfo l : client.getActiveGames()) {
                    activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
                }
            }
        });
    }

    public void onCreateLobbyClicked()
    {
        CreateLobbyMessage clm = (CreateLobbyMessage) MessageFactory.getMessage("CLB-MSG");
        clm.setPlayer1Id(client.getUser().getId());
        client.update(clm);
    }

    public void onJoinLobbyClicked()
    {
        // TODO : Set the lobby ID
        ConnectToLobbyMessage clm = (ConnectToLobbyMessage) MessageFactory.getMessage("CNT-MSG");
        clm.setPlayer2(client.getUser().getUsername());
        clm.setStartTime(LocalDateTime.now());
        client.update(clm);
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);

        Platform.runLater(() -> {
            for (LobbyInfo l : client.getActiveGames()) {
                activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
            }
        });
    }
}
