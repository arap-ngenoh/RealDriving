/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import real.settings.Preference;
import real.settings.Preferences;
import real.util.LibraryAssistantUtil;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class AdminController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField pass;
    private final static Logger LOGGER = LogManager.getLogger(AdminController.class.getName());
      

    Preference preference;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preference = Preference.getPreference();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
     
                 String uname = username.getText();
        String pword = DigestUtils.shaHex(pass.getText());


     if (uname.equals(preference.getUsername()) && pword.equals(preference.getPassword())) {
            closeStage();
            Parent parent = FXMLLoader.load(getClass().getResource("/realdriving/FXMLDocument.fxml"));
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
    private void handleCancelButtonAction(ActionEvent event) throws IOException {
    
          closeStage();
            Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
            stage.setScene(new Scene(parent));
            stage.show();
            LibraryAssistantUtil.setStageIcon(stage);

       
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

   
  public void loadMain() throws IOException {
      
            Parent parent = FXMLLoader.load(getClass().getResource("/realdriving/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
            stage.setScene(new Scene(parent));
            stage.fullScreenProperty().not();
            stage.show();
            LibraryAssistantUtil.setStageIcon(stage);
       
    }
public void logout() throws IOException{

            closeStage();
            Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
            stage.setScene(new Scene(parent));
            stage.show();
            LibraryAssistantUtil.setStageIcon(stage);
        }
       
}