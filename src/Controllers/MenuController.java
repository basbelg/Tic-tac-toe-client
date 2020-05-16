package Controllers;

import Client.Client;
import DataClasses.LobbyInfo;
import Messages.*;
import com.mysql.cj.protocol.Message;
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

public class MenuController implements BaseController, Initializable
{
    //CREATELOBBY SENDS OUT A CREATE LOBBY MESSAGE TO SERVER
    //ACTIVE LOBBIES LIST SHOULD BE A LOCAL FIELD CLIENT SIDE (IN CLIENT CLASS)
    private Client client;
    public Button createLobbyButton;
    public Button logoutButton;
    public ListView activeGamesList;
    public Button joinLobbyButton;
    public Button spectateButton;
    public Button vsAIButton;
    public Button accountButton;
    public Label errorLabel;
    public Label welcomeLabel;

    public void onLogoutClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
            Parent root = loader.load();
            LoginController lc = loader.getController();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            client.terminateThread();
            stage.close();
            stage.setTitle("Login");
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
            }
            else if (msg instanceof CreateAIGameMessage)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                    Parent root = loader.load();
                    BoardController bc = loader.getController();
                    bc.passInfo(client, msg, 1);
                    Stage stage = (Stage) vsAIButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Tic-Tac-Toe (Vs. AI)");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (msg instanceof CreateLobbyMessage)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                    Parent root = loader.load();
                    BoardController bc = loader.getController();
                    bc.passInfo(client, msg, 1);
                    Stage stage = (Stage) createLobbyButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Tic-Tac-Toe (Vs. Player)");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (msg instanceof SpectateMessage) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Board.fxml"));
                    Parent root = loader.load();
                    BoardController bc = loader.getController();
                    bc.passInfo(client, msg, 0);
                    Stage stage = (Stage) joinLobbyButton.getScene().getWindow();
                    stage.close();
                    stage.setTitle("Tic-Tac-Toe (Spectating)");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (msg instanceof InactiveGameMessage) {
                activeGamesList.getItems().clear();
                for (LobbyInfo l : client.getActiveGames()) {
                    activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
                }
                if(client.getCurrentGameId().equals(((InactiveGameMessage) msg).getFinishedGameId()))
                {
                    finishedWaitingForServer();
                    errorLabel.setText("This lobby is not longer active!");
                    client.setCurrentGameId("No Game");
                }
            }
            else if (msg instanceof NewAILobbyMessage) {
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
            else if (msg instanceof ConnectFailedMessage)
            {
                errorLabel.setText(msg.toString());
                finishedWaitingForServer();
            }
            else if (msg instanceof UpdateAccountInfoMessage)
            {
                welcomeLabel.setText("Welcome, " + client.getUser().getUsername() + ".");
            }
        });
    }

    public void onCreateLobbyClicked()
    {
        CreateLobbyMessage clm = (CreateLobbyMessage) MessageFactory.getMessage("CLB-MSG");
        clm.setPlayer1Id(client.getUser().getId());
        waitForServer();
        client.update(clm);
    }

    public void onAccountClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
            Parent root = loader.load();
            AccountController ac = loader.getController();
            ac.passInfo(client);
            Stage stage = (Stage) accountButton.getScene().getWindow();
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

    public void onJoinLobbyClicked()
    {
        if(activeGamesList.getItems().size() <= 0)
        {
            errorLabel.setText("There are no active games to join!");
        }
        else if(activeGamesList.getSelectionModel().getSelectedIndex() < 0)
        {
            errorLabel.setText("Please select a game to join!");
        }
        else if(client.getActiveGames().get(activeGamesList.getSelectionModel().getSelectedIndex()).getPlayerCount() == 2)
        {
            errorLabel.setText("This lobby is full! Try spectating.");
        }
        else if(activeGamesList.getSelectionModel().getSelectedIndex() >= 0)
        {
            ConnectToLobbyMessage clm = (ConnectToLobbyMessage) MessageFactory.getMessage("CNT-MSG");

            clm.setLobbyGameId(client.getActiveGames().get(activeGamesList.getSelectionModel().getSelectedIndex()).getLobbyId());
            clm.setPlayer2(client.getUser().getUsername());
            clm.setStartTime(LocalDateTime.now());
            waitForServer();
            client.update(clm);
        }

    }

    public void onSpectateClicked()
    {
        if(activeGamesList.getItems().size() <= 0)
        {
            errorLabel.setText("There are no active games to spectate!");
        }
        else if(activeGamesList.getSelectionModel().getSelectedIndex() < 0)
        {
            errorLabel.setText("Please select a game to spectate!");
        }
        else if(client.getActiveGames().get(activeGamesList.getSelectionModel().getSelectedIndex()).getPlayerCount() < 2)
        {
            errorLabel.setText("This game has not started yet!");
        }
        else if(activeGamesList.getSelectionModel().getSelectedIndex() >= 0)
        {
            SpectateMessage spm = (SpectateMessage) MessageFactory.getMessage("SPC-MSG");
            spm.setSpectatorId(client.getUser().getId());
            spm.setGameId(client.getActiveGames().get(activeGamesList.getSelectionModel().getSelectedIndex()).getLobbyId());
            waitForServer();
            client.update(spm);
        }

    }
    public void onVsAIClicked()
    {
        CreateAIGameMessage cam = (CreateAIGameMessage) MessageFactory.getMessage("CAI-MSG");
        cam.setPlayer1Id(client.getUser().getId());
        cam.setStartTime(LocalDateTime.now());
        waitForServer();
        client.update(cam);
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);

        Platform.runLater(() -> {
            for (LobbyInfo l : client.getActiveGames()) {
                activeGamesList.getItems().add(new Label(l.getCreatorUsername() + "\'s Game\t " + l.getPlayerCount() + "/2"));
            }

            welcomeLabel.setText("Welcome, " + client.getUser().getUsername() + ".");
        });
    }

    private void waitForServer()
    {
        createLobbyButton.setDisable(true);
        logoutButton.setDisable(true);
        spectateButton.setDisable(true);
        accountButton.setDisable(true);
        joinLobbyButton.setDisable(true);
        vsAIButton.setDisable(true);
    }

    private void finishedWaitingForServer()
    {
        createLobbyButton.setDisable(false);
        logoutButton.setDisable(false);
        spectateButton.setDisable(false);
        accountButton.setDisable(false);
        joinLobbyButton.setDisable(false);
        vsAIButton.setDisable(false);
    }
}
