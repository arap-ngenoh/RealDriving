
package real.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import newpackage.AdminController;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class AdminsetController implements Initializable {

    @FXML
    private JFXPasswordField newpass;
    @FXML
    private JFXTextField adminusername;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      

    }

   
    private void initDefaultValues() {
        Preference preference = Preference.getPreference();
     
        adminusername.setText(String.valueOf(preference.getUsername()));
        String passHash = String.valueOf(preference.getPassword());
        newpass.setText(passHash.substring(0, Math.min(passHash.length(), 10)));
       
    }

    @FXML
    private void save(ActionEvent event) {
          String admn= adminusername.getText();
       
        String pas = newpass.getText();
     

        Preference preference = Preference.getPreference();
        
            preference.setUsername(admn);
        preference.setPassword(pas);
       
        Preference.writePreferenceToFile(preference);
         Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("changes made successfully");
            alert.showAndWait();
            alert.close();
            
       
        
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        AdminController log = new AdminController();
        log.loadMain();
    }

}
