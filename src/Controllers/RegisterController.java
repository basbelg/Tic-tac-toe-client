package Controllers;

import Client.Client;
import DataClasses.User;
import Messages.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements BaseController, Initializable
{
    //IF ACCOUNTSUCCESSFULMESSAGE IS RECIEVED, UPDATE FIELDS IN CLIENT OBJECT
    private Client client;
    private User newUser;
    public Button confirmButton;
    public Button cancelButton;
    public TextField enterFirstName;
    public TextField enterLastName;
    public TextField enterUsername;
    public TextField enterPassword;
    public TextField enterConfirmPassword;
    public Label errorFNameLabel;
    public Label errorLNameLabel;
    public Label errorUsernameLabel;
    public Label errorPasswordLabel;
    public Label errorConfirmPasswordLabel;
    public Label errorLabel;
    public Label titleLabel;

    public void onConfirmClicked()
    {
        errorFNameLabel.setText("");
        errorLNameLabel.setText("");
        errorUsernameLabel.setText("");
        errorPasswordLabel.setText("");
        errorConfirmPasswordLabel.setText("");
        errorLabel.setText("");

        if(!enterFirstName.getText().equals("") && !enterLastName.getText().equals("") && !enterUsername.getText().equals("") &&
                !enterPassword.getText().equals("") && !enterConfirmPassword.getText().equals("") &&
                enterPassword.getText().equals(enterConfirmPassword.getText()))
        {
            waitingForServer();
            newUser = new User(enterUsername.getText(), enterFirstName.getText(), enterLastName.getText(), enterPassword.getText());
            if(confirmButton.getText().equals("Register")) {
                CreateAccountMessage cam = (CreateAccountMessage) MessageFactory.getMessage("CAC-MSG");
                cam.setNewUser(newUser);
                client.update(cam);
            }
            else if (confirmButton.getText().equals("Confirm")) {
                newUser.setId(client.getUser().getId());
                UpdateAccountInfoMessage uam = (UpdateAccountInfoMessage) MessageFactory.getMessage("UPA-MSG");
                uam.setUpdatedUser(newUser);
                client.update(uam);
            }
        }
        else
        {
            Platform.runLater(() -> {
                if (enterFirstName.getText().equals("")) {
                    errorFNameLabel.setText("Please enter your first name!\n");
                }
                if (enterLastName.getText().equals("")) {
                    errorLNameLabel.setText("Please enter your last name!\n");
                }
                if (enterUsername.getText().equals("")) {
                    errorUsernameLabel.setText("Please enter a valid username!\n");
                }
                if (enterPassword.getText().equals("")) {
                    errorPasswordLabel.setText("Please enter a valid password!\n");
                }
                if (!enterPassword.getText().equals(enterConfirmPassword.getText()) && !enterPassword.getText().equals("")) {
                    errorConfirmPasswordLabel.setText("Passwords do NOT match!\n");
                }
            });
        }

    }

    public void onCancelClicked()
    {
        if(confirmButton.getText().equals("Register"))
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
                Parent root = loader.load();
                LoginController lc = loader.getController();
                Stage stage = (Stage) confirmButton.getScene().getWindow();
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
        else if(confirmButton.getText().equals("Confirm"))
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
                Parent root = loader.load();
                AccountController ac = loader.getController();
                ac.passInfo(client);
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
                stage.setTitle("Account");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void update(Serializable msg)
    {
        Platform.runLater(() -> {
                if (msg instanceof AccountSuccessfulMessage) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Login.fxml"));
                        Parent root = loader.load();
                        LoginController lc = loader.getController();
                        Stage stage = (Stage) confirmButton.getScene().getWindow();
                        stage.close();
                        client.terminateThread();
                        stage.setTitle("Login");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (msg instanceof AccountFailedMessage) {
                    finishedWaitingForServer();
                    errorLabel.setText(msg.toString());
                }
                else if (msg instanceof UpdateAccountInfoMessage) {
                    if(confirmButton.isDisable())
                    {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Account.fxml"));
                            Parent root = loader.load();
                            AccountController ac = loader.getController();
                            ac.passInfo(client);
                            Stage stage = (Stage) confirmButton.getScene().getWindow();
                            stage.close();
                            stage.setTitle("Account");
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        User newUser = ((UpdateAccountInfoMessage) msg).getUpdatedUser();
                        enterUsername.setText(newUser.getUsername());
                        enterFirstName.setText(newUser.getFirstName());
                        enterLastName.setText(newUser.getLastName());
                        enterPassword.setText(newUser.getPassword());
                        enterConfirmPassword.setText(newUser.getPassword());
                    }

                }
        });
    }

    public void passInfo(Client client)
    {
        this.client = client;
        client.setController(this);

        Platform.runLater(() -> {
            if (confirmButton.getText().equals("Confirm")) {
                titleLabel.setText("Modify Account");
                enterUsername.setText(client.getUser().getUsername());
                enterFirstName.setText(client.getUser().getFirstName());
                enterLastName.setText(client.getUser().getLastName());
                enterPassword.setText(client.getUser().getPassword());
                enterConfirmPassword.setText(client.getUser().getPassword());
            }
            else
            {
                titleLabel.setText("Register");
            }
        });
    }

    private void waitingForServer()
    {
        cancelButton.setDisable(true);
        confirmButton.setDisable(true);
    }

    private void finishedWaitingForServer()
    {
        cancelButton.setDisable(false);
        confirmButton.setDisable(false);
    }
}
