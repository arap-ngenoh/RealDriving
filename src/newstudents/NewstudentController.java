/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newstudents;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import real.data.model.Member;
import real.database.DataHelper;
import icons.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class NewstudentController implements Initializable {
    

    @FXML
    private TextField fname;
    @FXML
    private TextField names;
    @FXML
    private TextField idno;
    @FXML
    private TextField phone;
    @FXML
    private TextField pdl;
    private TextField tdb;
    private TextField dl;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton clear;
    private TextField fee;
    DatabaseHandler handler;
    private AnchorPane rootpane;
    @FXML
    private GridPane mainpane;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainCointainer;
    @FXML
    private ComboBox<String> category;
 

    /**
     * Initializes the controller class.
     */
    
      
    @FXML
    private TextField dlno;
    @FXML
    private TextField tdbno;
    @FXML
    private TextField feecat;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
category.getItems().removeAll( category.getItems());
 category.getItems().addAll("D1 Category","C1 Category","B1 category","A2 Category","Special Category","Refreshers Category");

   
    }    

    @FXML
    private void save(ActionEvent event) throws SQLException, ClassNotFoundException {
        String f_name=fname.getText();
        String name=names.getText();
        String id_no=idno.getText();
        String phone_no=phone.getText();
        String pdl_no=pdl.getText();
        String tdb_no=tdbno.getText();
        String dl_no=dlno.getText();
        String fecat=feecat.getText();
       
       String categor=category.getSelectionModel().getSelectedItem();
       
          Boolean flag = f_name.isEmpty()|| name.isEmpty() || id_no.isEmpty()||phone_no.isEmpty()||categor.isEmpty();
          if (flag) {
            Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("All Field Are Required");
            alert.showAndWait();
            alert.close();
        }
      else{
         
  
            if (DataHelper.isMemberExists(id_no)) {
            Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("ID number already regesterd ");
            alert.showAndWait();
            alert.close();
        }

         else{
        int fecatt=Integer.parseInt(fecat);
        Member member = new Member(f_name, name,id_no, phone_no, pdl_no,tdb_no,dl_no,categor,fecatt);
        boolean result = DataHelper.insertNewMember(member);
        

       
           if(result ){
                  Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("Student Registration Succesful ");
            alert.showAndWait();
            alert.close();
               }
            }
      }    
    }   
    
    private void clearEntries() {
  
    }
        
        
        
        
     

    @FXML
    private void clear(ActionEvent event) {
        fname.clear();
        names.clear();
        idno.clear();
        phone.clear();
        pdl.clear();
        tdbno.clear();
        dlno.clear();
        feecat.clear();
       
    }
    public void feecheker(){
  
    
        
        }
    
    
   
    
}