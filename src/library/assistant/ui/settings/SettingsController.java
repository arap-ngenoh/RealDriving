/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import real.settings.Preferences;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
      
        String uname = username.getText();
        String pass = password.getText();

        Preferences preferences = Preferences.getPreferences();
       
        preferences.setUsername(uname);
        preferences.setPassword(pass);

        Preferences.writePreferenceToFile(preferences);
    }
    
}
