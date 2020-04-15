package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController
{
    public Button modAccountButton;
    public Button gameHistoryButton;
    public Button backButton;

    public void onBackClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Menu.fxml"));
            Parent root = loader.load();
            MenuController mc = loader.getController();
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

    public void onModAccountClicked()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Register.fxml"));
            Parent root = loader.load();
            RegisterController rc = loader.getController();
            Stage stage = (Stage) modAccountButton.getScene().getWindow();
            stage.close();
            stage.setTitle("Modify Account");
            rc.confirmButton.setText("Confirm");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
