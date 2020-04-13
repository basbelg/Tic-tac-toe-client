package Game;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    public Button signInButton;
    public Button newUserButton;
    public TextField enterUsername;
    public TextField enterPassword;
    public Label invalidLabel;

    public List<Player> users;

    public void onUsernameChanged()
    {
        if(!enterPassword.getText().equals("") && !enterUsername.getText().equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }

    public void onPasswordChanged()
    {
        if(!enterUsername.getText().equals("") && !enterPassword.getText().equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }

    public void onSignInClicked()
    {
        users= new ArrayList<>();
        users.add(new Player("1", "Jon", "J", "O", "a"));
        users.add(new Player("1", "Bas", "J", "O", "b"));
        users.add(new Player("1", "Ril", "J", "O", "c"));

        Player p = new Player("1", enterUsername.getText(), "J", "O", enterPassword.getText());

        if(contains(p))
        {
            invalidLabel.setText("Logged in");
        }
        else
        {
            invalidLabel.setText("INVALID CREDENTIALS");
        }

    }

    public void onNewUserClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
            Parent root = loader.load();
            RegisterController rc = loader.getController();
            Stage stage = (Stage) newUserButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Register");
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

    public boolean contains(Player p)
    {
        for(Player player : users)
        {
            if(player.getUserName().equals(p.getUserName()) && player.getPassword().equals(p.getPassword()))
            {
                return true;
            }
        }
        return false;
    }
}
