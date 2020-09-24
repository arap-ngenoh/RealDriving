/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank_recods;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import enqure.EnqureController;
import icons.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class InputController implements Initializable {
    ObservableList<InputModel> data = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<String> type;
    @FXML
    private DatePicker date;
    @FXML
    private TextField amount;
    @FXML
    private JFXButton save;
    @FXML
    private TextField personel;
    @FXML
    private TableView<InputModel> p_display;
    @FXML
    private TableColumn<InputModel, Object> namecol;
    @FXML
    private TableColumn<InputModel, Integer> amountcol;
    @FXML
    private TableColumn<InputModel, Object> datecol;
    @FXML
    private TableColumn<InputModel, Object> typecol;
    @FXML
    private JFXButton clear1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.getItems().removeAll( type.getItems());
        type.getItems().addAll("Deposit","Withdraw");
         initcol();
         tablodoldur();
    } 
    public void clear(){
        personel.clear();
        amount.clear();
        type.getSelectionModel().clearSelection();
    }

    @FXML
    private void save(ActionEvent event) {
        String typee= type.getSelectionModel().getSelectedItem();
        LocalDate dat=date.getValue();
        String amount1=amount.getText();
        String personel1=personel.getText();
     Boolean flag=type.getSelectionModel().isEmpty()||amount1.isEmpty()||personel1.isEmpty();
      if(flag){
           Alert alert=new  Alert(Alert.AlertType.ERROR);
            alert.setContentText("This Fields Should Not Be Blank");
            alert.showAndWait();
             alert.close(); 
      }
      
      try {
            int amnt=Integer.parseInt(amount1);
                  String query = "INSERT INTO bank(type,personel,dat,amounr) VALUES ("
                +"'"+typee+"',"+
              "'"+ personel1+"',"+
                 "'"+date.getValue()+"',"+
               ""+amnt+""+
                
                ")";
           DatabaseHandler handler = DatabaseHandler.getInstance();
              if(  handler.execAction(query)){
            Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("You've added Records Succefully");
            alert.showAndWait();
            alert.close();
            clear();
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

    private void clear(ActionEvent event) {
        clear();
    }
      private void initcol() {
                namecol.setCellValueFactory(new PropertyValueFactory<>("personel"));
                amountcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
                datecol.setCellValueFactory(new PropertyValueFactory<>("dat"));
                typecol.setCellValueFactory(new PropertyValueFactory<>("type"));    }
    
    private void tablodoldur()
    {
          LocalDate time = LocalDate.now();
           data.clear();
           data = FXCollections.observableArrayList();
          try{
          DatabaseHandler handler = DatabaseHandler.getInstance();
            String SQL="SELECT personel, amounr,dat,type FROM bank";// ON student.id=fees.id   
            ResultSet rs = handler.execQuery(SQL);
          while (rs.next()) {
                String name = rs.getString("personel").toUpperCase();
                String typee = rs.getString("type").toUpperCase();
                Date datee = rs.getDate("dat");
                int hey= rs.getInt("amounr");
                

                data.add(new InputController.InputModel(name,hey,datee,typee));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EnqureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        p_display.setItems(data);
   
            }

    @FXML
    private void remarks(ActionEvent event) {
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
    }

    @FXML
    private void clear1(ActionEvent event) {
    }
    
    public class InputModel{
        String personel;
        int amount;
        Date dat;
        String type;

        public InputModel(String personel, int amount, Date dat, String type) {
            this.personel = personel;
            this.amount = amount;
            this.dat = dat;
            this.type = type;
        }

        public String getPersonel() {
            return personel;
        }

        public int getAmount() {
            return amount;
        }

        public Date getDat() {
            return dat;
        }

        public String getType() {
            return type;
        }
        
        
    
}
    
}
