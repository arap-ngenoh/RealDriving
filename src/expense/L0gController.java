package expense;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import real.settings.Preferences;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class L0gController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(L0gController.class.getName());

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    Preferences preference;
    @FXML
    private JFXButton login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preference = Preferences.getPreferences();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        String uname = username.getText();
        String pword = DigestUtils.shaHex(password.getText());

       if (uname.equals(preference.getUsername()) && pword.equals(preference.getPassword())) {
            closeStage();
            Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
              stage.getIcons().add(new Image("/icons/Streer.PNG"));
            stage.setScene(new Scene(parent));
            stage.show();
            LOGGER.log(Level.INFO, "User successfully logged in {}", uname);
      }
       else {
            Alert alert=new  Alert(Alert.AlertType.ERROR);
     alert.setContentText("wrong-credentials ");
     alert.showAndWait();
     alert.close();
         
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    public void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() throws IOException {
    
         
            Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
              stage.getIcons().add(new Image("/icons/Streer.PNG"));
            stage.setScene(new Scene(parent));
            stage.show();
       
       
    }

}
