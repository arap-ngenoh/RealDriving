/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expense;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import icons.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class ExpensesController implements Initializable {

    @FXML
    private JFXComboBox<String> type;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amount;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton clear;
    @FXML
    private TextField personel;
       DatabaseHandler handler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      type.getItems().removeAll( type.getItems());
 type.getItems().addAll("Printing Expense","Rent payment","vehicle maintaince","petrol purchase","salaries and wadges","electricity","water bills ","misleneous expenses","other expenses");

    }    

    @FXML
    private void save(ActionEvent event) {
        String typee= type.getSelectionModel().getSelectedItem();
        String person= personel.getText();
        String val = amount.getText();
          
       if(type.getSelectionModel().isEmpty()||person.isEmpty()||val.isEmpty()){
             Alert alert=new  Alert(Alert.AlertType.ERROR);
            alert.setContentText("Type, Person spent, Date and Amount fields required");
            alert.showAndWait();
             alert.close();
       }
        try {
            int amnt=Integer.parseInt(val);
                  String query = "INSERT INTO expenses(type,personel,dat,amount) VALUES ("
                +"'"+typee+"',"+
              "'"+ person+"',"+
                 "'"+date.getValue()+"',"+
               ""+amnt+""+
                
                ")";
     handler = DatabaseHandler.getInstance();
              if(  handler.execAction(query)){
               Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("expense updated successfuly ");
            alert.showAndWait();
            alert.close();
           }
        else{
        Alert alert=new  Alert(Alert.AlertType.ERROR);
            alert.setContentText("error in adding expenses please try again");
            alert.showAndWait();
             alert.close();
        }
    }
        catch(Exception e){
        
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        amount.clear();
        personel.clear();
        type.requestFocus();
    }
    
}
