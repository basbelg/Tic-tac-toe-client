package Client;

import Controllers.BaseController;
import Controllers.BoardController;
import Controllers.MenuController;
import Controllers.RegisterController;
import DataClasses.User;
import DataClasses.*;
import Messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {
    private BaseController controller;
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Thread thread;
    private int port;
    private User user;
    private List<LobbyInfo> allActiveGames;
    private String currentGameId;

    public Client(BaseController controller)
    {
        port = 8000;
        this.controller = controller;
        this.allActiveGames = new ArrayList<>();
        currentGameId = "No Game";
        thread = new Thread(this);
        thread.setDaemon(true); // Close thread when window closes
        thread.start();
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("localhost", port);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            while(!thread.isInterrupted()) {
                //read input from server
                Packet p = (Packet)input.readObject();  // WHEN RECEIVING A NewLobbyMessage OR A NewAILobbyMessage: APPEND
                                                        // TO THE ACTIVE LOBBIES LIST, AND CHECK IF THE CURRENT CONTROLLER
                                                        // IS AN instanceof THE VsPlayerController BEFORE CALLING update
                String type = p.getType();
                switch(type) {
                    case "ACF-MSG":
                        AccountFailedMessage afm = (AccountFailedMessage)p.getData();
                        controller.update(afm);
                        break;
                    case "ACS-MSG":
                        AccountSuccessfulMessage asm = (AccountSuccessfulMessage)p.getData();
                        controller.update(asm);
                        break;
                    case "AAG-MSG":
                        AllActiveGamesMessage agm = (AllActiveGamesMessage)p.getData();
                        allActiveGames = agm.getAllActiveGames();
                        controller.update(agm);
                        break;
                    case "CNT-MSG":
                        ConnectToLobbyMessage clm = (ConnectToLobbyMessage)p.getData();
                        currentGameId = clm.getLobbyGameId();
                        controller.update(clm);
                        break;
                    case "CAI-MSG":
                        CreateAIGameMessage calm = (CreateAIGameMessage)p.getData();
                        currentGameId = calm.getGameLobbyId();
                        controller.update(calm);
                        break;
                    case "CLB-MSG":
                        CreateLobbyMessage clbm = (CreateLobbyMessage)p.getData();
                        currentGameId = clbm.getGameLobbyId();
                        controller.update(clbm);
                        break;
                    case "COF-MSG":
                        ConnectFailedMessage cfm = (ConnectFailedMessage)p.getData();
                        controller.update(cfm);
                    case "FUL-MSG":
                        FullLobbyMessage flm = (FullLobbyMessage)p.getData();
                        for(LobbyInfo lobbyInfo : allActiveGames) {
                            if(lobbyInfo.getLobbyId().equals(flm.getLobbyGameId())) {
                                lobbyInfo.setPlayerCount(2);
                                break;
                            }
                        }
                        if(controller instanceof MenuController)
                        {
                            controller.update(flm);
                        }
                        break;
                    case "GLG-MSG":
                        GameLogMessage glm = (GameLogMessage)p.getData();
                        controller.update(glm);
                        break;
                    case "GRE-MSG":
                        GameResultMessage grm = (GameResultMessage)p.getData();
                        currentGameId = "No Game";
                        controller.update(grm);
                        break;
                    case "GMP-MSG":
                        GamesPlayedMessage gpm = (GamesPlayedMessage)p.getData();
                        controller.update(gpm);
                        break;
                    case "GVW-MSG":
                        GameViewersMessage gvm = (GameViewersMessage)p.getData();
                        controller.update(gvm);
                        break;
                    case "ILM-MSG":
                        IllegalMoveMessage ilm = (IllegalMoveMessage)p.getData();
                        controller.update(ilm);
                        break;
                    case "IAG-MSG":
                        InactiveGameMessage igm = (InactiveGameMessage)p.getData();
                        for(int i = 0; i < allActiveGames.size(); i++)
                        {
                            if(allActiveGames.get(i).getLobbyId().equals(igm.getFinishedGameId()))
                            {
                                allActiveGames.remove(i);
                                break;
                            }
                        }
                        if(controller instanceof MenuController || (currentGameId.equals(igm.getFinishedGameId()) && controller instanceof BoardController))
                        {
                            controller.update(igm);
                        }
                        break;
                    case "LEM-MSG":
                        LegalMoveMessage lmm = (LegalMoveMessage)p.getData();
                        controller.update(lmm);
                        break;
                    case "LOF-MSG":
                        LoginFailedMessage lfm = (LoginFailedMessage)p.getData();
                        controller.update(lfm);
                        break;
                    case "LOS-MSG":
                        LoginSuccessfulMessage lsm = (LoginSuccessfulMessage)p.getData();
                        controller.update(lsm);
                        break;
                    case "NAI-MSG":
                        NewAILobbyMessage naim = (NewAILobbyMessage)p.getData();
                        allActiveGames.add(new LobbyInfo(naim.getCreatorUsername(), naim.getGameLobbyId(), 2));
                        if(controller instanceof MenuController)
                        {
                            controller.update(naim);
                        }
                        break;
                    case "NLB-MSG":
                        NewLobbyMessage nlm = (NewLobbyMessage)p.getData();
                        allActiveGames.add(new LobbyInfo(nlm.getCreatorUsername(), nlm.getGameLobbyId(), 1));
                        if(controller instanceof MenuController)
                        {
                            controller.update(nlm);
                        }
                        break;
                    case "SPC-MSG":
                        SpectateMessage spm = (SpectateMessage)p.getData();
                        controller.update(spm);
                        break;
                    case "SSP-MSG":
                        StopSpectatingMessage ssm = (StopSpectatingMessage)p.getData();
                        currentGameId = "No Game";
                        controller.update(ssm);
                        break;
                    case "STS-MSG":
                        StatsMessage stm = (StatsMessage)p.getData();
                        controller.update(stm);
                        break;
                    case "UPA-MSG":
                        UpdateAccountInfoMessage upm = (UpdateAccountInfoMessage)p.getData();
                        user = upm.getUpdatedUser();
                        if(controller instanceof MenuController || controller instanceof RegisterController)
                        {
                            controller.update(upm);
                        }
                        break;

                    default:
                        System.out.println("ERROR");
                }
            }
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("client thread terminated");
        }
    }

    public void terminateThread() {
        thread.interrupt();
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(Serializable msg) {

        try {
            if(msg instanceof AllActiveGamesMessage) {
                Packet p = new Packet("AAG-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof ConcedeMessage) {
                Packet p = new Packet("CNC-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof ConnectToLobbyMessage) {
                Packet p = new Packet("CNT-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof CreateAccountMessage) {
                Packet p = new Packet("CAC-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof CreateAIGameMessage) {
                Packet p = new Packet("CAI-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof CreateLobbyMessage) {
                Packet p = new Packet("CLB-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof DeactivateAccountMessage) {
                Packet p = new Packet("DAC-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof GameLogMessage) {
                Packet p = new Packet("GLG-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof GamesPlayedMessage) {
                Packet p = new Packet("GMP-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof GameViewersMessage) {   // CHANGE FROM HERE
                Packet p = new Packet("GVW-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof InactiveGameMessage)
            {
                Packet p = new Packet("IAG-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof LoginMessage)
            {
                Packet p = new Packet("LOG-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof MoveMessage)
            {
                Packet p = new Packet("MOV-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof SpectateMessage)
            {
                Packet p = new Packet("SPC-MSG", msg);
                currentGameId = ((SpectateMessage) msg).getGameId();
                output.writeObject(p);
            }
            else if (msg instanceof StopSpectatingMessage)
            {
                Packet p = new Packet("SSP-MSG", msg);
                output.writeObject(p);
            }
            else if(msg instanceof StatsMessage)
            {
                Packet p = new Packet("STS-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof UpdateAccountInfoMessage)
            {
                Packet p = new Packet("UPA-MSG", msg);
                output.writeObject(p);
            }
            else {
                System.out.println("Client Update - ERROR");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setController(BaseController con) {
        controller = con;
    }


    public List<LobbyInfo> getActiveGames() {
        return allActiveGames;
    }

    public String getCurrentGameId() {
        return currentGameId;
    }

    public void setCurrentGameId(String currentGameId) {
        this.currentGameId = currentGameId;
    }
}