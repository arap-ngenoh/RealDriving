/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print;

import com.jfoenix.controls.JFXButton;
import fees.ViewfeeController;
import icons.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import real.util.LibraryAssistantUtil;
import report.DailyController;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class Print1Controller implements Initializable {
    ObservableList<Print1Controller.memg> listg = FXCollections.observableArrayList();


    @FXML
    private TableColumn<memg, Object> namecol;
    @FXML
    private TableColumn<memg, Object> amountcol;
    @FXML
    private TableColumn<memg, Object> datecol;
    @FXML
    private TableColumn<memg, Object> modecol;
    @FXML
    private TableColumn<memg, Object> personelcol;
    @FXML
    private TableColumn<memg, Object> typecol;
    @FXML
    private TableColumn<memg, Object> amouncol;
    @FXML
    private TableColumn<memg, Object> datcol;
    @FXML
    private StackPane rootpane;
    @FXML
    private AnchorPane contentpane;
    @FXML
    private TableView<memg> table;
    @FXML
    private JFXButton print;
    DatabaseHandler handler;
    @FXML
    private ComboBox<String> recotype;
    @FXML
    private DatePicker fromdate;
    @FXML
    private JFXButton loadreco;
    @FXML
    private Label date;
    String id,todatee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         recotype.getItems().removeAll( recotype.getItems());
        recotype.getItems().addAll("Daily reconciliation","weekly reconciliation","monthly reconciliation", "quarterly reconciliation","semi annual reconcisiliation","annual reconciliation");
    table();
   
  
   
    } 
    
    private void table(){
        
        namecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        amountcol.setCellValueFactory(new PropertyValueFactory<>("amount"));  
        datecol.setCellValueFactory(new PropertyValueFactory<>("day"));
        modecol.setCellValueFactory(new PropertyValueFactory<>("mode"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("fname1"));
        personelcol.setCellValueFactory(new PropertyValueFactory<>("spentby"));  
        amouncol.setCellValueFactory(new PropertyValueFactory<>("amount1")); 
        datcol.setCellValueFactory(new PropertyValueFactory<>("datee")); 
       
    }
 

      
     private Stage getStage() {
        return (Stage) table.getScene().getWindow();
    }

    @FXML
    private void printrepor(ActionEvent event) {
        List<List> printData = new ArrayList<>();
        String[] headers = { " INCOME FROM ","RECEIVED","DATE","Pay Mode","Spend by ","Expense Type","AMOUNT SPEND","DATE"};
        printData.add(Arrays.asList(headers));
        List<String> row = new ArrayList<>();
        for (memg book1 : listg) {
            
            row.add(book1.getFname());
            row.add(" "+book1.getAmount());
            row.add(" "+book1.getDay());
            row.add(" "+book1.getMode());
            row.add(" "+book1.getFname1());
            row.add(" "+book1.getSpentby());
            row.add(" "+book1.getFname1());
            row.add(" "+book1.getAmount1());
            row.add(" "+book1.getDatee());
            printData.add(row);

        }
        LibraryAssistantUtil.initPDFExprot(rootpane, contentpane, getStage(), printData);
    
    }

    @FXML
    private void loaddate(ActionEvent event) throws SQLException {
        load();
    }

    @FXML
    private void loadreco(ActionEvent event) throws SQLException {
        load();
    }

    @FXML
    private void loadvalue(MouseEvent event) throws SQLException {
         load();
    }
    
       public class memg{
     String fname;
    int amount;
   String day;
   String mode;
   String fname1;
  String spentby;
  int amount1;
  String datee; 

        public memg(String fname, int amount, String day, String mode, String fname1, String spentby, int amount1, String datee) {
            this.fname = fname;
            this.amount = amount;
            this.day = day;
            this.mode = mode;
            this.fname1 = fname1;
            this.spentby = spentby;
            this.amount1 = amount1;
            this.datee = datee;
        }

        public String getFname() {
            return fname;
        }

        public int getAmount() {
            return amount;
        }

        public String getDay() {
            return day;
        }

        public String getMode() {
            return mode;
        }

        public String getFname1() {
            return fname1;
        }

        public String getSpentby() {
            return spentby;
        }

        public int getAmount1() {
            return amount1;
        }

        public String getDatee() {
            return datee;
        }
        
        
        }
        public void print(){
               int amount ;
               String fname ;
               String names ;
               String day ;
               String mode;
               String  fname1;
               String spentby ;
               int amount1 ;
               String datee ;
            
         String prin="SELECT Student.fname,student.names,student.id,instalment.paydate, instalment.paymode,instalment.amount AS SUMAMOUN,expenses.personel, expenses.type, expenses.dat, expenses.amount FROM student LEFT OUTER  JOIN instalment ON student.id=instalment.id LEFT OUTER JOIN expenses ON instalment.paydate=expenses.dat WHERE  paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')|| expenses.dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"') group by sumamoun,expenses.amount;";
        
           String qu =  " SELECT personel, type, dat, amount from expenses \n" 
               + " WHERE dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
           String qu2 =  " SELECT Student.fname,student.names,student.id,instalment.paydate, instalment.paymode,instalment.amount SUMAMOUN "
                + "FROM student LEFT OUTER  JOIN instalment "
                + "ON student.id=instalment.id   WHERE  paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
        try {
            handler=DatabaseHandler.getInstance();
             ResultSet rs = handler.execQuery(prin);
            while (rs.next()) {
               amount = rs.getInt("SUMAMOUN");
               fname = rs.getString("fname").toUpperCase();
               names = rs.getString("names").toUpperCase();
                day=rs.getString("paydate").toUpperCase();
              mode=rs.getString("paymode").toUpperCase();
                fname1= rs.getString("type").toUpperCase();//getString("type").toUpperCase();
              spentby = rs.getString("personel");
               amount1 = rs.getInt("amount");
              datee=rs.getString("dat");
   
                listg.add(new Print1Controller.memg(fname+"   "+names,amount,day,mode,fname1, spentby,amount1,datee));
                  }
                  table.setItems(listg);    

           
        } catch (SQLException ex) {
            Logger.getLogger(ViewfeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        }
          private void load() throws SQLException{
        String res=recotype.getSelectionModel().getSelectedItem();
        
        if(res.equalsIgnoreCase("Daily reconciliation")){
                listg.clear();
               id=fromdate.getValue().toString();
               todatee=id;
              date.setText(""+todatee);
             print();
            
        }
        if(res.equalsIgnoreCase("weekly reconciliation")){
                        listg.clear();
                       id=fromdate.getValue().toString();
                       todatee=fromdate.getValue().plusWeeks(1).toString();
                       date.setText(""+todatee);
               print();
        }
        if(res.equalsIgnoreCase("monthly reconciliation")){
                        listg.clear();

              id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(1).toString();
          date.setText(""+todatee);
           table();
               print();
        }
        if(res.equalsIgnoreCase("quarterly reconciliation")){
                        listg.clear();
               id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(3).toString();
         date.setText(""+todatee);
              print();
        }
        if(res.equalsIgnoreCase("semi annual reconcisiliation")){
            listg.clear();
               id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(6).toString();
        date.setText(""+todatee);
       // date.setFont();
              print();
        }
        if(res.equalsIgnoreCase("annual reconciliation")){
                  listg.clear();
               id=fromdate.getValue().toString();
               todatee=fromdate.getValue().plusMonths(12).toString();
               date.setText(""+todatee);
             print();
        }
        if(res.equalsIgnoreCase("")){
        }
       
        }
        
    }

    


