package government;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import icons.DatabaseHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import real.util.LibraryAssistantUtil;

public class GovenmentController implements Initializable {
    ObservableList<GovenmentController.Member> list = FXCollections.observableArrayList();

    @FXML
    private Tab govntab;
    @FXML
    private JFXButton save;
    @FXML
    private Tab viewgovntpay;
    @FXML
    private TableView<Member> governmentview;
    @FXML
    private TableColumn<Member, String> namecol;
    @FXML
    private TableColumn<Member, String> pdlcol;
    @FXML
    private TableColumn<Member, String> tdbcol;
    @FXML
    private TableColumn<Member, String> eoccol;
    @FXML
    private TableColumn<Member, String> dlcol;
  DatabaseHandler handler ;
    @FXML
    private TextField pdl2;
    @FXML
    private TextField tbd2;
    @FXML
    private TextField dl2;
    @FXML
    private TextField eoc2;
    @FXML
    private TextField id2;
    @FXML
    private DatePicker date;
    @FXML
    private JFXButton print;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TextField RECEIPT;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initcol();
        loadcol();
    }    

    @FXML
    private void save(ActionEvent event) {
        String pdl1=pdl2.getText();
        String tdb1=tbd2.getText();
        String dl1=dl2.getText();
        String eoc1=eoc2.getText();
        String id1=id2.getText();
        String recept=RECEIPT.getText();
        Boolean flag=pdl1.isEmpty()||tdb1.isEmpty()||dl1.isEmpty()||eoc1.isEmpty()||id1.isEmpty()||recept.isEmpty();
        if(flag){
            Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            alert.close();
        }
        else{
        String dat=date.getValue().toString();
         String quess="INSERT INTO government(id,receipt,pdl,tdb,eoc,dl,paydate) VALUES ("
         +"'"+id1+"',"+
         ""+recept+","+
         ""+pdl1+","+
         "'"+tdb1+"',"+
         "'"+dl1+"',"+
         "'"+eoc1+"',"+
         "'"+dat+"'"+
         ")"; 
      handler = DatabaseHandler.getInstance();
      if(handler.execAction(quess));{
      Alert alert=new  Alert(Alert.AlertType.INFORMATION);
     alert.setContentText("Fee payment successful ");
     alert.showAndWait();
     alert.close();
    } 
    }
    }

    @FXML
    private void govntab(Event event) {
        initcol();
        loadcol();
    }

    @FXML
    private void viewgovntpay(Event event) {
          initcol();
        loadcol();
    }

    private void initcol() {
                namecol.setCellValueFactory(new PropertyValueFactory<>("fmame"));
                pdlcol.setCellValueFactory(new PropertyValueFactory<>("pd"));
                tdbcol.setCellValueFactory(new PropertyValueFactory<>("td"));
                eoccol.setCellValueFactory(new PropertyValueFactory<>("eo"));
                dlcol.setCellValueFactory(new PropertyValueFactory<>("d"));
                
             

        
    }

    private void loadcol() {
         list.clear();

   handler = DatabaseHandler.getInstance();
        String qu1 = "SELECT * FROM student";
        String qu = "SELECT student.fname,student.names,government.pdl,government.tdb,government.eoc,government.dl FROM student RIGHT OUTER JOIN government ON student.id=government.id";
        ResultSet rs = handler.execQuery(qu);
        try {
          while (rs.next()) {
                String fmame = rs.getString("fname").toUpperCase();
                String name = rs.getString("names").toUpperCase();
                String pd = rs.getString("pdl").toUpperCase();
                String td= rs.getString("tdb").toUpperCase();
                String d = rs.getString("dl").toUpperCase();
                String eo = rs.getString("eoc");

                list.add(new GovenmentController.Member(fmame+"   "+name,pd,td,d,eo));

            }
        } catch (SQLException ex) {
            Logger.getLogger(GovenmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        governmentview.setItems(list);
    }
 private Stage getStage() {
        return (Stage) governmentview.getScene().getWindow();
    }
    @FXML
    private void print(ActionEvent event) {
            List<List> printData = new ArrayList<>();
          String[] headers = {"     student name ","   PDL FEE","  TDB FEE","   DL FEE","    EOC FEE "};
        printData.add(Arrays.asList(headers));
        for (GovenmentController.Member member : list ) {
            List<String> row = new ArrayList<>();
            row.add(member.getFmame());
            row.add(""+member.getPd());
            row.add(""+member.getTd());
            row.add(""+member.getD());
            row.add(""+member.getEo());
            printData.add(row);
    }
                LibraryAssistantUtil.initPDFExprot(rootPane, contentPane, getStage(), printData);

    }


       public static class Member{
        private final SimpleStringProperty fmame;
        //private final SimpleStringProperty names;
        private final SimpleStringProperty pd;
        private final SimpleStringProperty td;
        private final SimpleStringProperty d;
        private final SimpleStringProperty eo;

        public Member(String fmame, String pd, String td, String d, String eo) {
            this.fmame = new SimpleStringProperty(fmame);
            this.pd = new SimpleStringProperty(pd);
            this.td =new SimpleStringProperty( td);
            this.d = new SimpleStringProperty(d);
            this.eo =new SimpleStringProperty( eo);
        }

        public String getFmame() {
            return fmame.get();
        }

        public String getPd() {
            return pd.get();
        }

        public String getTd() {
            return td.get();
        }

        public String getD() {
            return d.get();
        }

        public String getEo() {
            return eo.get();
        }

        
        }
    

}
  
        
       
    
    

