
package report;

import com.jfoenix.controls.JFXButton;
import fees.ViewfeeController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
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
     int withbal;
     int depobal;
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
    @FXML
    private TableColumn<Member, String> modecol;
    @FXML
    private TextField totdeposit;
    @FXML
    private TextField totwithdawal;
   
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
        modecol.setCellValueFactory(new PropertyValueFactory<>("mode"));
    }
    public void data()  {
     handler = DatabaseHandler.getInstance();
        list.clear();
             
               
        String qu =  " SELECT Student.fname,student.names,student.id,instalment.paydate, instalment.paymode,instalment.amount SUMAMOUN "
                + "FROM student LEFT OUTER  JOIN instalment "
                + "ON student.id=instalment.id   WHERE  paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int amount = rs.getInt("SUMAMOUN");
               String fname = rs.getString("fname").toUpperCase();
               String names = rs.getString("names").toUpperCase();
               String day=rs.getString("paydate").toUpperCase();
               String mode=rs.getString("paymode").toUpperCase();
             int total=0;
           
                

                list.add(new DailyController.Member(fname+"   "+names,amount,day,mode));
             

            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewfeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

       tableView.setItems(list);
      
    }
    private void table(){
        
        
        expensecol.setCellValueFactory(new PropertyValueFactory<>("fname1"));
        spendbycol.setCellValueFactory(new PropertyValueFactory<>("spentby"));  
        amountcol.setCellValueFactory(new PropertyValueFactory<>("amount1")); 
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
    private void printreport(ActionEvent event) throws IOException {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/print/print1.fxml"));
            Parent parent = loader.load();

            Stage stag = new Stage(StageStyle.DECORATED);
            stag.setTitle("Print Report ----");
            stag.setScene(new Scene(parent));
             stag.initModality(Modality.APPLICATION_MODAL);
            stag.show();
            LibraryAssistantUtil.setStageIcon(stag);
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
     
         
        
      


    
    public class Member {
    String fname;
    int amount;
   String day;
   String mode;
   
   
   

    public Member(String fname, int amount,String day,String mode) {
        this.fname = fname;
        this.amount = amount;
        this.day=day;
        this.mode=mode;
        
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
        public String getMode() {
            return mode;
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
   
      
       String qu ="SELECT SUM(amount) as tt from instalment WHERE PAYMODE='cash' AND paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        ba =c.getInt("tt");
       totalexpense.setText(""+ba);
      
       }
}
 
  private void bala() throws SQLException{
      totalincome.clear();
        handler = DatabaseHandler.getInstance();
       String qu ="SELECT SUM(amount) as tt from expenses WHERE dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        bal =c.getInt("tt");
       totalincome.setText(""+bal);
      
       }
  }
  
   
  private void bankdepo() throws SQLException{
     totdeposit.clear();
        handler = DatabaseHandler.getInstance();
       String qu ="SELECT SUM(amounr) as tt from bank WHERE type='Deposit' AND dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        depobal =c.getInt("tt");
      totdeposit.setText(""+depobal);
      
       }
  }
  
  
   
  private void bankwith() throws SQLException{
      totwithdawal.clear();
        handler = DatabaseHandler.getInstance();
       String qu ="SELECT SUM(amounr) as tt from bank WHERE type='Withdraw' AND dat BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"')";
       ResultSet c =handler.execQuery(qu);
        while (c.next()) {
        withbal =c.getInt("tt");
     totwithdawal.setText(""+withbal);
      
       }
  }
  
  
  
        public void incom(){
         int add=(ba+withbal)-(bal+depobal);
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
              bankdepo();
              bankwith();
              incom();
            
        }
        if(res.equalsIgnoreCase("weekly reconciliation")){
                        list.clear();
                        list1.clear();
                       id=fromdate.getValue().toString();
                       todatee=fromdate.getValue().plusWeeks(1).toString();
                       date.setText(""+todatee);
              data1();
              data();
             balance();
             bala();
             bankdepo();
              bankwith();
             incom();
        }
        if(res.equalsIgnoreCase("monthly reconciliation")){
                          list.clear();
                        list1.clear();

              id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(1).toString();
          date.setText(""+todatee);
               data1();
              data();
             balance();
             bala();
             bankdepo();
              bankwith();
             incom();
        }
        if(res.equalsIgnoreCase("quarterly reconciliation")){
                        list.clear();
                        list1.clear();
               id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(3).toString();
         date.setText(""+todatee);
             data1();
              data();
             balance();
             bala();
             bankdepo();
              bankwith();
             incom();
        }
        if(res.equalsIgnoreCase("semi annual reconcisiliation")){
               id=fromdate.getValue().toString();
         todatee=fromdate.getValue().plusMonths(6).toString();
        date.setText(""+todatee);
       // date.setFont();
             data1();
             balance();
            bala();
               data();
           incom();
        }
        if(res.equalsIgnoreCase("annual reconciliation")){
                  list.clear();
                 list1.clear();
               id=fromdate.getValue().toString();
               todatee=fromdate.getValue().plusMonths(12).toString();
               date.setText(""+todatee);
             data1();
             balance();
            bala();
            data();
            bankdepo();
              bankwith();
           incom();
        }
        if(res.equalsIgnoreCase("")){
        }
       
        }
        
     
        
        
        public void plint(){
          
    
            
        }
        
     }