/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newstudents;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import real.database.DataHelper;
import icons.DatabaseHandler;
import studentlist.MemberListController;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class UpdateController implements Initializable {

    @FXML
    private GridPane mainpane;
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
    @FXML
    private TextField tdb;
    @FXML
    private TextField dl;
    @FXML
    private JFXButton save;
    private TextField fee;
    @FXML
    private ComboBox<String> category;
  DatabaseHandler handler;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     category.getItems().removeAll( category.getItems());
 category.getItems().addAll("D1 Category","C1 Category","B1 category","A2 Category","Special Category","Refreshers Category");

    }    

    @FXML
    private void save(ActionEvent event) throws ClassNotFoundException {
         String f_name=fname.getText();
        String name=names.getText();
        String id_no=idno.getText();
        String phone_no=phone.getText();
        String pdl_no=pdl.getText();
        String tdb_no=tdb.getText();
        String dl_no=dl.getText();
       String categor=category.getSelectionModel().getSelectedItem();
  if (DataHelper.isMemberExists(id_no)) {
          
       handler=DatabaseHandler.getInstance();
        String update="UPDATE  student SET fname='"+ f_name+"',names='"+name+"',phone='"+phone_no+"',pdlnumber='"+pdl_no+"',tdbnumber='"+tdb_no+"',dlnumber='"+dl_no+"',category='"+categor+"' WHERE id='"+id_no+"'";
          if(  handler.execAction(update)){

          Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("Students's details updated successfully   ");
            alert.showAndWait();
            alert.close();
            
        }
          else{
          Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("update was not made please try again  ");
            alert.showAndWait();
            alert.close();
          
          }
    }
   
     else{
        Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText(" student with id "+id_no+"\n Does not exits please add him / her first ");
            alert.showAndWait();
            alert.close();
  }
  }
        public void infalteUI(MemberListController.Member member) {

        fname.setText(member.getFmame());
        names.setText(member.getNames());
        idno.setText(member.getIdno());
       phone.setText(member.getPhone());
       pdl.setText(member.getPdl());
      tdb.setText(member.getTdb());
       dl.setText(member.getDl());
     // category.setText(member.getCategory()); 
    

    }
}
