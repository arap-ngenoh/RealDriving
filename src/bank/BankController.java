/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import com.jfoenix.controls.JFXButton;
import enqure.EnqureController;
import icons.DatabaseHandler;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class BankController implements Initializable {
    ObservableList<BankController.DeModel> data = FXCollections.observableArrayList();
    ObservableList<BankController.WiModel> data1 = FXCollections.observableArrayList();
    ObservableList<BankController.WiModel> data2 = FXCollections.observableArrayList();

    public static Node load(URL resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 DatabaseHandler handler;
               String name ;
                String typee;
                Date datee;
                int hey;
                 String name1 ;
                String typee1;
                Date datee1;
                int hey1;
                 int  bal;
                 
     String id,todatee;
    private DatePicker todate;
    @FXML
    private ComboBox<String> recotype;
    @FXML
    private DatePicker fromdate;
    @FXML
    private JFXButton loadreco;
    @FXML
    private Label date;
    @FXML
    private TextField totaldeposit;
    @FXML
    private TextField totalwithdraw;
    @FXML
    private TableView<WiModel> tableView;
    @FXML
    private TableColumn<WiModel, Object> namedcol;
    @FXML
    private TableColumn<WiModel, Object> amountdcol;
    @FXML
    private TableColumn<WiModel, Object> datdcol;
    @FXML
    private TableView<DeModel> tableView1;
    @FXML
    private TableColumn<DeModel, Object> namewcol;
    @FXML
    private TableColumn<DeModel, Object> amountwcol;
    @FXML
    private TableColumn<DeModel, Object> datewcol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         initcol();
        initcol1();   
    }    

    @FXML
    private void loaddate(ActionEvent event) {
    }

    @FXML
    private void loadreco(ActionEvent event) throws SQLException {
       
      load();
    }


    
     private void initcol() {
                namedcol.setCellValueFactory(new PropertyValueFactory<>("personel"));
                amountdcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
                datdcol.setCellValueFactory(new PropertyValueFactory<>("dat"));
                //typedcol.setCellValueFactory(new PropertyValueFactory<>("type")); 
     }
     
      private void initcol1() {
                namewcol.setCellValueFactory(new PropertyValueFactory<>("personel"));
                amountwcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
                datewcol.setCellValueFactory(new PropertyValueFactory<>("dat"));
                //typedcol.setCellValueFactory(new PropertyValueFactory<>("type")); 
     }
 
    
    
       private void tablodoldur()
    {
          LocalDate time = LocalDate.now();
           data.clear();
           data = FXCollections.observableArrayList();
          try{
     handler = DatabaseHandler.getInstance();
            String SQL="SELECT personel, amounr,dat,type FROM bank where type = 'Withdraw' AND dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";// ON student.id=fees.id   
            ResultSet rs = handler.execQuery(SQL);
          while (rs.next()) {
                String nam = rs.getString("personel").toUpperCase();
                String type = rs.getString("type").toUpperCase();
                Date datt = rs.getDate("dat");
                int heye= rs.getInt("amounr");
                

                data.add(new BankController.DeModel(nam,heye,datt,type));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EnqureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView1.setItems(data);
   
            }
          private void tablodoldur1()
    {
          LocalDate time = LocalDate.now();
           data1.clear();
           data1 = FXCollections.observableArrayList();
          try{
           handler = DatabaseHandler.getInstance();
            String SQL="SELECT type, amount,dat,type FROM bank where type = 'Deposit'and dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";// ON student.id=fees.id   
            ResultSet rs = handler.execQuery(SQL);
              
            while (rs.next()) {
                 name = rs.getString("type").toUpperCase();
                 typee = rs.getString("type").toUpperCase();
                 datee = rs.getDate("dat");
                 hey= rs.getInt("amount");
                

               data1.add(new BankController.WiModel(name,hey,datee,typee));

            }
                       //handler = DatabaseHandler.getInstance();

             String SQ="SELECT student.fname as personel, instalment.amount,paymode as type, instalment.paydate as dat FROM student  RIGHT OUTER join instalment on student.id=instalment.id where paymode = 'Pay Bill'|| PAYMODE='Bankers cheque' AND paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";// ON student.id=fees.id   
            ResultSet rs1 = handler.execQuery(SQ);
              
            while (rs1.next()) {
                 name1 = rs1.getString("personel").toUpperCase();
                 typee1 = rs1.getString("type").toUpperCase();
                 datee1 = rs1.getDate("dat");
                 hey1= rs1.getInt("amount");
                

                data1.add(new BankController.WiModel(name1,hey1,datee1,typee1));
                 
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnqureController.class.getName()).log(Level.SEVERE, null, ex);
        }
   tableView.setItems(data1);
        
      
   
            }
          
       private void bala() throws SQLException{
           totalwithdraw.clear();
           handler = DatabaseHandler.getInstance();
       String qu ="SELECT SUM(amounr) as tt from bank WHERE type='Withdraw' AND dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
      
 ResultSet c =handler.execQuery(qu);
       while (c.next()) {
      int  balt = c.getInt("tt");
      
        
      
       totalwithdraw.setText(""+balt);
      
       }
          }
       
           private void bala1() throws SQLException{
               totaldeposit.clear();
           handler = DatabaseHandler.getInstance();
       String qu ="select sum(t.amount) as total FROM (select amounr as amount from bank where bank.type='deposit' AND dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"') union all select sum(instalment.amount) as amount from instalment where paymode ='Pay Bill'|| paymode='Bankers Cheque' AND instalment.paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"'))t ";
       ResultSet c =handler.execQuery(qu);
       while (c.next()) {
           
      bal =c.getInt("total");
      totaldeposit.setText(""+bal);
      
       //totaldeposit.setText(""+bal);
      
       }
           }
  //  handler = DatabaseHandler.getInstance();
       
    
    public class DeModel{
        String personel;
        int amount;
        Date dat;
        String type;

        public DeModel(String personel, int amount, Date dat, String type) {
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
    
      public class WiModel{
        String personel;
        int amount;
        Date dat;
        String type;

        public WiModel(String personel, int amount, Date dat, String type) {
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
      
      private void load() throws SQLException{
        String res=recotype.getSelectionModel().getSelectedItem();
        
        if(res.equalsIgnoreCase("Daily reconciliation")){
                  data.clear();
                  data1.clear();
                  id=fromdate.getValue().toString();
                  todatee=id;
                  date.setText(""+todatee);
                tablodoldur();
                tablodoldur1();
             //   bala();
              bala1();
             // studfee();
            
        }
         if(res.equalsIgnoreCase("weekly reconciliation")){
                        data.clear();
                        data1.clear();
                       id=fromdate.getValue().toString();
                       todatee=fromdate.getValue().plusWeeks(1).toString();
                       date.setText(""+todatee);
                   tablodoldur();
                tablodoldur1();
               bala();
              bala1();
            
         }
        
        if(res.equalsIgnoreCase("monthly reconciliation")){
                            data.clear();
                             data1.clear();
              id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(1).toString();
          date.setText(""+todatee);
               tablodoldur();
                tablodoldur1();
             bala();
              bala1();
             
        }
        if(res.equalsIgnoreCase("quarterly reconciliation")){
                           data.clear();
                  data1.clear();
               id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(3).toString();
         date.setText(""+todatee);
             tablodoldur();
                tablodoldur1();
              bala();
              bala1();
             ;
        }
        if(res.equalsIgnoreCase("semi annual reconcisiliation")){
               data.clear();
                  data1.clear();
               id=fromdate.getValue().toString();
         todatee=todate.getValue().plusMonths(6).toString();
        date.setText(""+todatee);
       tablodoldur();
                tablodoldur1();
             bala();
              bala1();
           
        }
        if(res.equalsIgnoreCase("annual reconciliation")){
                  data.clear();
                 data1.clear();
               id=fromdate.getValue().toString();
               todatee=fromdate.getValue().plusMonths(12).toString();
               date.setText(""+todatee);
                tablodoldur();
                tablodoldur1();
               bala();
              bala1();
       
            
        }
       
        }
        
        public class amount{
        int values;

        public amount(int values) {
            this.values = values;
        }

        public int getValues() {
            return values;
        }

        public void setValues(int values) {
            this.values = values;
        }
        
        }
    
}
