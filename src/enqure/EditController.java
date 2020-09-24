/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enqure;

import com.jfoenix.controls.JFXButton;
import icons.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class EditController implements Initializable {
DatabaseHandler handler;
    @FXML
    private DatePicker date;
    @FXML
    private TextField name;
    @FXML
    private TextField contact;
    @FXML
    private TextArea message;
    @FXML
    private JFXButton save;
 int i;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
         handler = DatabaseHandler.getInstance();
  String typee= message.getText();
       String person= name.getText();
      String val = contact.getText();
      Boolean flag=typee.isEmpty()||person.isEmpty()||val.isEmpty();
      if(flag){
      
           Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText(" Date, Name and his or her contacts are required ");
            alert.showAndWait();
            alert.close();
      }
      else{
        try {
       
        String re= "UPDATE  enquiries SET message='"+typee+"',"+"person = '"+person+"'"+",dat= '"+date.getValue()+"',"+"contact= '"+val+"'"+" WHERE id="+i;
        if( handler.execAction(re)){
           Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("updated  successfully");
            alert.showAndWait();
            alert.close();

   
           }
        else{
        Alert alert=new  Alert(Alert.AlertType.ERROR);
            alert.setContentText("error  occurred ...\n please try again");
            alert.showAndWait();
             alert.close();
        }
    }
        catch(Exception e){
        
        }
      }
    }
      public void infalteUI(EnqureController.enquire member) {
     
     String nam =member.getPerson().toUpperCase();
     name.setText(nam);
     String dat=member.getDate();
     //date.setValue(dat);
     String contac =member.getPhone();
     contact.setText(contac);
      i =member.getId();
      String text=member.getMessage();
      message.setText(text);
    // label.setText(" add remarks to Enquiry of " +" "+ name +"  "+ "phone number "+"  "+  contact   +"\n "+  "made on"+"  " +data);
}    

}