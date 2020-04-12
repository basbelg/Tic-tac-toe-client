package Game;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login
{
    public Button signInButton;
    public Button newUserButton;
    public TextField enterUsername;
    public TextField enterPassword;

    public void onUsernameChanged()
    {
        if(!enterPassword.equals(""))
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
        if(!enterUsername.equals(""))
        {
            signInButton.setDisable(false);
        }
        else
        {
            signInButton.setDisable(true);
        }
    }
}
