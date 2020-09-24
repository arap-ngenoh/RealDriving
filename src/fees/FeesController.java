
package fees;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import icons.DatabaseHandler;
import studentlist.MemberListController;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class FeesController implements Initializable {

    @FXML
    private TextField idnumber;
    @FXML
    private TextField amount;
    @FXML
    private JFXButton save;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
       DatabaseHandler handler,handler1;
    @FXML
    private ComboBox<String> paymode;
    @FXML
    private TextField receipt;
  
    @FXML
    private TextField vhire;
    @FXML
    private TextField registration;
    
    @FXML
    private Tab mpya;
    private Tab others;
    @FXML
    private DatePicker paydate;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    paymode.getItems().removeAll( paymode.getItems());
 paymode.getItems().addAll("cash","Pay Bill","Bankers cheque");


    }    

    @FXML
    private void save(ActionEvent event) {
    
  
         String amoun=amount.getText();
        String id_no=idnumber.getText();
  
        String hirev=vhire.getText();
        String regi=registration.getText();
        String receit=receipt.getText();
        String pay=paymode.getSelectionModel().getSelectedItem();
     
 
     
        
        
          Boolean flag = amoun.isEmpty() || id_no.isEmpty()||receit.isEmpty()||paymode.getSelectionModel().isEmpty();
     if (flag) {
            Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("All Field Are Required"
                    + "  Only registration and vihicle hire fee "
                    + " you can use  take 0 entries");
            alert.showAndWait();
            alert.close();
        }
     else{
        Boolean chat=hirev.isEmpty()||regi.isEmpty();
       String payd=paydate.getValue().toString();
       int amon=Integer.parseInt(amoun);
     if(chat){
     String quess="INSERT INTO instalment(id,amount,paymode,receipt,paydate) VALUES ("
         +"'"+id_no+"',"+
         ""+amon+","+
         "'"+pay+"',"+
         "'"+receit+"',"+
         "'"+payd+"'"+
         ")"; 
      handler = DatabaseHandler.getInstance();
      handler.execAction(quess);
     }
      
     
       else{
       int hire=Integer.parseInt(hirev);
       int reg=Integer.parseInt(regi);
 String que="INSERT INTO fees (id,registration,vhire,paydate) VALUES ("
         +"'"+id_no+"',"+
         ""+reg+","+
         ""+hire+","+
         "'"+payd+"'"+
         ")";

 String ques="INSERT INTO instalment(id,amount,paymode,receipt,paydate) VALUES ("
         +"'"+id_no+"',"+
         ""+amon+","+
         "'"+pay+"',"+
         "'"+receit+"',"+
         "'"+payd+"'"+
         ")"; 
  handler = DatabaseHandler.getInstance();
  handler1 = DatabaseHandler.getInstance();
 if(handler.execAction(ques) && handler1.execAction(que)){
     Alert alert=new  Alert(Alert.AlertType.INFORMATION);
     alert.setContentText("fee payment successful ");
     alert.showAndWait();
     alert.close();
       amount.clear();
        idnumber.clear();
  
       vhire.clear();
       registration.clear();
      receipt.clear();
 }
 
 
 else{
     Alert alert=new  Alert(Alert.AlertType.ERROR);
     alert.setContentText(" Error Occured");
     alert.showAndWait();
     alert.close();
 } 
    }
    }
    }
    private void clearEntries() {
     amount.clear();
     idnumber.clear();
    }

      public void infalteUI(FeebalController.Member member) {
  vhire.setText(""+member.getVhire());
 registration.setText(""+member.getReg());
 receipt.setText(""+member.getFee());
 paymode.getSelectionModel().getSelectedItem();
idnumber.setText(member.getId_no());
amount.setText(""+member.getAmount());
  
     // category.setText(member.getCategory()); 
    

    }


}