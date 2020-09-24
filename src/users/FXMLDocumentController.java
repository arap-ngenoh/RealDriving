
package users;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import icons.DatabaseHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import newpackage.AdminController;
import newstudents.NewstudentController;
import real.util.LibraryAssistantUtil;

/**
 *
 * @author kRich
 */
public class FXMLDocumentController implements Initializable {
   DatabaseHandler hundler;
    @FXML
    private JFXButton newstudent;
    @FXML
    private JFXButton pay;
    @FXML
    private JFXButton expense;
    @FXML
    private JFXButton enquiry;
    private String url=null;
    @FXML
    private BorderPane main;
    @FXML
    private Menu studentlist;
    @FXML
    private MenuItem editstudent;
    @FXML
    private Menu file;
    @FXML
    private MenuItem viewfees;
    @FXML
    private MenuItem feebal;
    @FXML
    private BorderPane mainpane;
    @FXML
    private JFXButton gok;
    @FXML
    public JFXButton reports;
    @FXML
    private JFXButton settings1;
    @FXML
    private MenuItem backup;
    @FXML
    private MenuItem regstatus;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem bank_rec;
    @FXML
    public MenuItem bank_records;
  
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
     public void centre( String rl) throws IOException{
         
           Parent root = FXMLLoader.load(getClass().getResource(url));
        main.setCenter(root);
     
        
      
          
      }

    @FXML
    private void newstudent(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/newstudents/newstudent.fxml";
        centre(url);
    }

    @FXML
    private void pay(ActionEvent event) throws IOException {
        
               main.setCenter(null);
        url="/fees/fees.fxml";
        centre(url); 
    
        
    }

    @FXML
    private void expense(ActionEvent event) throws IOException {
          main.setCenter(null);
        url="/expense/expenses.fxml";
        centre(url);
    }

    @FXML
    private void enquiry(ActionEvent event) throws IOException {
          main.setCenter(null);
        url="/enqure/enqure.fxml";
        centre(url); 
    }

    

   @FXML
   private void editstudent(ActionEvent event) throws IOException {
          main.setCenter(null);
        url="/studentlist/member_list.fxml";
        centre(url); 
    }
 
  

    @FXML
    private void studentlist(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/studentlist/member_list.fxml";
        centre(url); 
    }

    private void handleRefresh(ActionEvent actionEvent) {
    }

    private void home() throws IOException {
    main.setCenter(null);
  Parent root = FXMLLoader.load(getClass().getResource("/expense/l0g.fxml"));
  main.setCenter(root);    }

    @FXML
    private void setings(ActionEvent event) throws IOException {
          main.setCenter(null);
        url="/real/settings/settings.fxml";
        centre(url); 
    }

    @FXML
    private void viewfees(ActionEvent event) throws IOException {
          main.setCenter(null);
        url="/fees/viewfee.fxml";
        centre(url); 
    }

    @FXML
    private void feebal(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/users/feebal.fxml";
        centre(url);
    }

    @FXML
    private void gok(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/government/govenment.fxml";
        centre(url);
    }

    @FXML
    private void reports(ActionEvent event) throws IOException {
           main.setCenter(null);
        url="/users/daily.fxml";
        centre(url);
    }

    @FXML
    private void backup(ActionEvent event) throws IOException {
        main.setCenter(null);
        url="/backup/backup.fxml";
        centre(url);
    }

    @FXML
    private void regsratus(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/regist/register.fxml";
        centre(url);
    }

    @FXML
    private void about(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backup/FXML.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("about");
            stage.setScene(new Scene(parent));
             stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            LibraryAssistantUtil.setStageIcon(stage);

            stage.setOnCloseRequest((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            Logger.getLogger(NewstudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bank_rec(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/users/input.fxml";
        centre(url);
    }

    @FXML
    private void bank_recds(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/users/bank.fxml";
        centre(url);
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/newpackage/admin.fxml";
        centre(url);
    }

    private void logout(ActionEvent event) throws IOException {
        String usernmane="admin";
        AdminController log = new AdminController();
        log.logout();
       
    }
   
  
    
}
