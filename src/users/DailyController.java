
package users;

import com.jfoenix.controls.JFXButton;
import fees.ViewfeeController;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import icons.DatabaseHandler;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import real.util.LibraryAssistantUtil;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class DailyController implements Initializable {
     ObservableList<DailyController.Member> list = FXCollections.observableArrayList();
     ObservableList<DailyController.Member1> list1 = FXCollections.observableArrayList();
      DatabaseHandler handler;
    @FXML
    private TextField totalexpense;
    @FXML
    private TextField totalincome;

    private AnchorPane contentPane;
     @FXML
    private TableView<Member> tableView;
     @FXML
    private TableColumn<Member,String> incomefromcol;
     @FXML
    private TableColumn<Member,String> amountreceivedcol;
     @FXML
    private TableView<Member1> tableView1;
     @FXML
    private TableColumn<Member1, String> expensecol;
     @FXML
    private TableColumn<Member1, String> spendbycol;
     @FXML
    private TableColumn<Member1, String> amountcol;
    @FXML
    private TextField balcar;
 int ba, bal;
    @FXML
    private ComboBox<String > recotype;
    @FXML
    private DatePicker fromdate;
    @FXML
    private JFXButton loadreco;
    /**
     * Initializes the controller class.
     */
     String id,todatee;
    private DatePicker todate;
    @FXML
    private Label date;
    @FXML
    private TableColumn<Member, String> datcol;
    @FXML
    private TableColumn<Member1, String> datecol;
    private StackPane rootPane;
    @FXML
    private JFXButton print1;
    @FXML
    private SplitPane holder;
    @FXML
    private AnchorPane holder1;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        recotype.getItems().removeAll( recotype.getItems());
        recotype.getItems().addAll("Daily reconciliation","weekly reconciliation","monthly reconciliation", "quarterly reconciliation","semi annual reconcisiliation","annual reconciliation");
              //  LocalDate time = LocalDate.now();

//                date.setText(""+time.format(DateTimeFormatter.ISO_DATE).toString());
         tablodoldur();
         table();
         
    }    
    private void tablodoldur()
    {             
         incomefromcol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        amountreceivedcol.setCellValueFactory(new PropertyValueFactory<>("amount"));  
        datcol.setCellValueFactory(new PropertyValueFactory<>("day"));
    }
    public void data()  {
     handler = DatabaseHandler.getInstance();
        list.clear();
             
               
        String qu =  " SELECT Student.fname,student.names,student.id,instalment.paydate,SUM(instalment.amount)AS SUMAMOUN "
                + "FROM student LEFT OUTER  JOIN instalment "
                + "ON student.id=instalment.id  WHERE PAYMODE='cash' AND paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"') GROUP BY student.id,instalment.paydate";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int amount = rs.getInt("SUMAMOUN");
               String fname = rs.getString("fname").toUpperCase();
               String names = rs.getString("names").toUpperCase();
               String day=rs.getString("paydate").toUpperCase();
             int total=0;
           
                

                list.add(new DailyController.Member(fname+"   "+names,amount,day));
             

            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewfeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

       tableView.setItems(list);
      
    }
    private void table(){
        
        
        expensecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        spendbycol.setCellValueFactory(new PropertyValueFactory<>("spentby"));  
        amountcol.setCellValueFactory(new PropertyValueFactory<>("amount")); 
        datecol.setCellValueFactory(new PropertyValueFactory<>("datee")); 
       
    }
    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }
     public void data1()  {
        handler = DatabaseHandler.getInstance();
        list1.clear();
            
               
        String qu =  " SELECT personel, type, dat, amount from expenses \n" 
               + " WHERE dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
               String  fname= rs.getString("type").toUpperCase();
               String spentby = rs.getString("personel");
               int amount = rs.getInt("amount");
               String datee=rs.getString("dat");
             int total=0;
           
            
                list1.add(new DailyController.Member1(fname, spentby,amount,datee));
             

            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewfeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

       tableView1.setItems(list1);
      
    }

    @FXML
    private void printreport(ActionEvent event) {
        String income= totalincome.getText();
        String expense=totalexpense.getText();
        String bbal=balcar.getText();
        List<List> printData = new ArrayList<>();
        
        String[] headers = { " INCOME FROM ","AMOUNT RECEIVED","DATE","Spend by ","AMOUNT SPEND  ","DATE  "};
        String[] footer = { "BALANCE BROUGHT DOWN DURING THE PERIOD"," :"+bbal," ","      ","    ","       "};
        String[] footers = { "  TOTAL INCOME FOR THE PERIOD:  ","KSH: "+income,"        ","TOTAL EXPENDICTURE:  "," "+expense,"       "};
        printData.add(Arrays.asList(headers));
        list.stream().map((member) -> {
            List<String> row = new ArrayList<>();
            row.add(member.getFname());
            row.add(""+member.getAmount());
            row.add(member.getDay());
             return row;
         }).map((row) -> {
             for (Member1 member1 : list1 ) {
                 row.add(member1.getSpentby());
                 row.add(""+member1.getAmount());
                 row.add(member1.getDatee());
                 
                 
             }
             return row;
         }).forEachOrdered((row) -> {
             printData.add(row);
         });
         printData.add(Arrays.asList(footers));
              printData.add(Arrays.asList(footer));
        LibraryAssistantUtil.initPDFExprot(rootPane, contentPane, getStage(), printData);
    }

    private void handleRefresh(ActionEvent event) throws SQLException {
         load();
    }


    @FXML
    private void loadreco(ActionEvent event) throws SQLException {
         load();
      
    }

    @FXML
    private void loaddate(ActionEvent event) throws SQLException {
     
      
       
    }
     
         
        
      

    @FXML
    private void bank_reports(ActionEvent event) throws IOException {
  
    }

    
    public class Member {
    String fname;
    int amount;
   String day;
   

    public Member(String fname, int amount,String day) {
        this.fname = fname;
        this.amount = amount;
        this.day=day;
        
    }

        public String getFname() {
            return fname;
        }

        public String getDay() {
            return day;
        }

        

        public int getAmount() {
            return amount;
        }
    }
    
    
    
    public class Member1 {
           private final SimpleStringProperty fname;
       
        private final SimpleStringProperty spentby;
        private final SimpleIntegerProperty amount;
        private final SimpleStringProperty datee;
        

       

    public Member1(String fname,String spentby, int amount,String datee) {
         this.fname = new SimpleStringProperty(fname);
            this.spentby = new SimpleStringProperty(spentby);
           this.amount=new SimpleIntegerProperty(amount);
            this.datee =new  SimpleStringProperty(datee);
        
    }

             public String getFname() {
                 return fname.get();
             }

             public String getSpentby() {
                 return spentby.get();
             }

             public int getAmount() {
                 return amount.get();
             }
             public String getDatee() {
                 return datee.get();
             }

       
}
    
     private void balance() throws SQLException{
         totalexpense.clear();
   handler = DatabaseHandler.getInstance();
   
      
       String qu ="SELECT SUM(amount) as tt from instalment WHERE PAYMODE='cash' AND  paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        ba =c.getInt("tt");
       totalexpense.setText(""+ba);
      
       }
}
 
  private void bala() throws SQLException{
      totalincome.clear();
        handler = DatabaseHandler.getInstance();
       String qu ="SELECT SUM(amount) as tt from expenses WHERE   dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        bal =c.getInt("tt");
       totalincome.setText(""+bal);
      
       }
  }
        public void incom(){
         int add=ba-bal;
                if(add<0){
                balcar.setText("Ksh  ("+ add+")");
                }
                else{
                 balcar.setText("Ksh  "+ add);
                }}

        private void load() throws SQLException{
        String res=recotype.getSelectionModel().getSelectedItem();
        
        if(res.equalsIgnoreCase("Daily reconciliation")){
                  list.clear();
                   list1.clear();
               id=fromdate.getValue().toString();
               todatee=id;
              date.setText(""+todatee);
              data1();
              data();
              balance();
              bala();
              incom();
            
        }
        if(res.equalsIgnoreCase("weekly reconciliation")){
                   list.clear();
                       alrt();
        }
        if(res.equalsIgnoreCase("monthly reconciliation")){
                      list.clear();
                       alrt();
        }
        if(res.equalsIgnoreCase("quarterly reconciliation")){
                        list.clear();
                       alrt();
        }
        if(res.equalsIgnoreCase("semi annual reconcisiliation")){
             list.clear();
                       alrt();
        }
        if(res.equalsIgnoreCase("annual reconciliation")){
             alrt();
        }
        if(res.equalsIgnoreCase("")){
            list.clear();
                       alrt();
        }
       
        
        
        
        
        
     }
        public void alrt(){
        Alert alert=new  Alert(Alert.AlertType.WARNING);
            alert.setContentText("Only admin allowed here----\nPlease log in as admin to access this section ");
            alert.showAndWait();
             alert.close();
        }
        
        
     }