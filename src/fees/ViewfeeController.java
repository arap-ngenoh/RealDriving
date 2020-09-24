/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import icons.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class ViewfeeController implements Initializable {
 ObservableList<ViewfeeController.Member> list = FXCollections.observableArrayList();

  DatabaseHandler handler;
    @FXML
    private TextField idsearch;
    @FXML
    private TextField namesearch;
    @FXML
    private JFXButton searchbt;
    @FXML
    private TableView<Member> feetable;
    @FXML
    private TextField amount;
    @FXML
    private TextField balance;
    @FXML
    private TableColumn<Member,String> amountcol;
    @FXML
    private TableColumn<Member,String> datecol;
    @FXML
    private TableColumn<Member,String> paymodecol;
    @FXML
    private TableColumn<Member,String> receiptcol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    
    }    

    @FXML
    private void id(ActionEvent event) {
    }
    int total;

    @FXML
    private void searchbt(ActionEvent event) throws SQLException {
      initCol();
      loadData();
       balance();
    }
   
 private void initCol() {
       amountcol.setCellValueFactory(new PropertyValueFactory<>("amountt"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("paydate"));
        paymodecol.setCellValueFactory(new PropertyValueFactory<>("paymode"));
        receiptcol.setCellValueFactory(new PropertyValueFactory<>("receipt"));
      
        
    }

  

    private void loadData() {
        list.clear();
 String id=idsearch.getText();
        handler = DatabaseHandler.getInstance();
        String qu ="SELECT student.fname,student.names,student.id,student.fees,\n"
                + "instalment.amount,instalment.paymode,instalment.receipt,instalment.paydate FROM student LEFT OUTER JOIN instalment\n"
                + "ON student.id=instalment.id\n"
                + " WHERE  student.id='"+id+"'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int amountt = rs.getInt("amount");
               String paymode = rs.getString("paymode").toUpperCase();
               String receipt = rs.getString("receipt").toUpperCase();
               String paydate =rs.getDate("paydate").toLocalDate().toString();
             
            
                

                list.add(new ViewfeeController.Member(amountt,  paymode,  receipt,  paydate));
                  String fmame = rs.getString("fname").toUpperCase();
                String name = rs.getString("names").toUpperCase();
                total =rs.getInt("fees");
                namesearch.setText(fmame+"  "+name);
                //amount.setText(""+total);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewfeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        feetable.setItems(list);
    }
   
public static class Member{
     private final SimpleIntegerProperty amountt;
        private final SimpleStringProperty paymode;
        private final SimpleStringProperty receipt;
        private final SimpleStringProperty paydate ;
       
      

        
        
     public Member( int amountt, String paymode,String receipt, String paydate) {
         
            this.amountt = new SimpleIntegerProperty (amountt);
           
            this.paymode = new SimpleStringProperty(paymode);
            this.receipt = new SimpleStringProperty (receipt);
            
            this.paydate = new SimpleStringProperty (paydate);
        }

        public Integer getAmountt() {
            return amountt.get();
        }

        public String  getPaymode() {
            return paymode.get();
        }

        public String getReceipt() {
            return receipt.get();
        }

        public String getPaydate() {
            return paydate.get();
        }

      
       
     
}
private void balance() throws SQLException{
    int tot;
        DatabaseHandler handler = DatabaseHandler.getInstance();
       String id=idsearch.getText();
       String qu ="SELECT id,SUM(amount) as tt from instalment WHERE id ='"+id+"'";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
       int bal =c.getInt("tt");
       amount.setText("KSHS: "+bal);
       tot=total-bal;
       balance.setText(""+tot);
       }
}

}
