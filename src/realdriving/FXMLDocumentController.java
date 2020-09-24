
package realdriving;

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
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import newstudents.NewstudentController;
import real.util.LibraryAssistantUtil;
import newpackage.AdminController;

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
    private MenuItem daillycash;
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
    private void daillycash(ActionEvent event) throws IOException {
          main.setCenter(null);
        //url="/report/daily.fxml";
        //centre();
    }

    private void weeklycash(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/report/monthly.fxml";
        centre(url);
    }

    private void monthlycash(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/report/weekly.fxml";
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
        url="/real/settings/adminset.fxml";
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
        url="/fees/feebal.fxml";
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
        url="/report/daily.fxml";
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
        url="/bank_recods/input.fxml";
        centre(url);
    }

    @FXML
    private void bank_recds(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/bank/bank.fxml";
        centre(url);
    }

    private void login(ActionEvent event) throws IOException {
         main.setCenter(null);
        url="/login/login.fxml";
        centre(url);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
      System.exit(1);
           /* Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
              stage.getIcons().add(new Image("/icons/Streer.PNG"));
            stage.setScene(new Scene(parent));
            stage.show();*/
             
    }
 
  
   public void logu() throws IOException{
       
     
     
            closeStage();
            Parent parent = FXMLLoader.load(getClass().getResource("/users/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Real Driving school");
              stage.getIcons().add(new Image("/icons/Streer.PNG"));
            stage.setScene(new Scene(parent));
            stage.show();
   }

    private void closeStage() {
        Stage stage=new Stage();
       stage.getScene().getWindow();
        stage.close();
        
    }
  
    
}
