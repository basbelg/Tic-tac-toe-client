package Client;

import Controllers.BaseController;
import DataClasses.GameInfo;
import DataClasses.LobbyInfo;
import DataClasses.User;

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

    public Client(BaseController controller)
    {
        port = 8000;
        this.controller = controller;
        this.allActiveGames = new ArrayList<>();
        thread = new Thread(this);
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
                    case "REG-MSG":
                        RegistrationMsg rm = (RegistrationMsg)p.getData();
                        for(String sc : rm.getSubscribedChannels()) {
                            subscribedChannels.add(sc);
                        }
                        controller.update(rm);
                        break;
                    case "PIC-MSG":
                        PictureMsg pm = (PictureMsg)p.getData();
                        controller.update(pm);
                        break;
                    case "CNG-MSG":
                        ChangeChannelMsg cm = (ChangeChannelMsg)p.getData();
                        controller.update(cm);
                        break;
                    case "TXT-MSG":
                        ChannelMsg tm = (ChannelMsg)p.getData();
                        controller.update(tm);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(Serializable msg) {
        try {
            if (msg instanceof ConnectToLobbyMessage) {
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
                Packet p = new Packet("LOG-MSG", msg);
                output.writeObject(p);
            }
            else if (msg instanceof GameViewersMessage) {   // CHANGE FROM HERE
                Packet p = new Packet("GVW-MSG", msg);
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


    public List<GameInfo> getGames() {
        return games;
    }
}