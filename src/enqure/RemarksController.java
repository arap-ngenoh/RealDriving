/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enqure;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import icons.DatabaseHandler;
import javafx.scene.control.Alert;
/**
 * FXML Controller class
 *
 * @author kRich
 */
public class RemarksController implements Initializable {
           DatabaseHandler handler;

    @FXML
    private Label label;
    @FXML
    private TextArea message2;
    @FXML
    private JFXButton save1;
  int id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void infalteUI(EnqureController.enquire member) {
     
     String name =member.getPerson().toUpperCase();
     String data=member.getDate();
     String contact =member.getPhone();
      id =member.getId();
     label.setText(" add remarks to Enquiry of " +" "+ name +"  "+ "phone number "+"  "+  contact   +"\n "+  "made on"+"  " +data);
}    

    @FXML
    private void save(ActionEvent event) {
                  handler = DatabaseHandler.getInstance();

       String t=message2.getText();
        String re= "UPDATE  enquiries SET remarks='"+t+"'  WHERE id="+id;
        if( handler.execAction(re)){
           Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("remark added successfully");
            alert.showAndWait();
            alert.close();
            
           }
        else{
             Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("error in adding remark \nplaese checkand try again");
            alert.showAndWait();
            alert.close();
    }
        
    }
    
}