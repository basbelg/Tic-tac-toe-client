package Game;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable
{
    public Button registerButton;
    public TextField enterFirstName;
    public TextField enterLastName;
    public TextField enterUsername;
    public TextField enterPassword;
    public TextField enterConfirmPassword;
    public Label errorLabel;

    public void onRegisterClicked()
    {
        StringBuffer error = new StringBuffer();
        if(enterFirstName.getText().equals(""))
        {
            error.append("Please enter your first name!\n");
        }
        else if(enterLastName.getText().equals(""))
        {
            error.append("Please enter your last name!\n");
        }
        else if(enterUsername.getText().equals(""))
        {
            error.append("Please enter a valid username!\n");
        }
        else if(enterPassword.getText().equals(""))
        {
            error.append("Please enter a valid password!\n");
        }
        else if(!enterPassword.getText().equals(enterConfirmPassword.getText()) && !enterPassword.getText().equals(""))
        {
            error.append("Passwords do NOT match!\n");
        }
        else if(!enterFirstName.getText().equals("") && !enterLastName.getText().equals("") && !enterUsername.getText().equals("") &&
                !enterPassword.getText().equals("") && !enterConfirmPassword.getText().equals("") &&
                enterPassword.getText().equals(enterConfirmPassword.getText()))
        {
            //do stuff
        }

        errorLabel.setText(error.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
