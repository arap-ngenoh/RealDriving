/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regist;

import fees.FeebalController;
import icons.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class RegisterController implements Initializable {
     ObservableList<RegisterController.Member> list = FXCollections.observableArrayList();


    @FXML
    private TableView<Member> table;
    @FXML
    private TableColumn<Member, String> namecol;
    @FXML
    private TableColumn<Member, String> idcol;
    @FXML
    private TableColumn<Member, String> regcol;
    @FXML
    private TableColumn<Member, String> vhirecol;
    @FXML
    private DatePicker datfom1;
    @FXML
    private DatePicker datto1;
 String id=null;
 String todatee=null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
     
        
    }  
    
       private void initCol() {
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id_no"));
        vhirecol.setCellValueFactory(new PropertyValueFactory<>("vhire"));
        regcol.setCellValueFactory(new PropertyValueFactory<>("reg"));
        
        
    }
    
     private void loadData() {
        list.clear();
  id = datfom1.getValue().toString();
  todatee=datto1.getValue().toString();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        String q= "SELECT student.fname,student.names,student.id,\n"
                + "Student.fees,SUM(fees.vhire) as tt, SUM(fees.registration) as ff \n"
                + " FROM student LEFT OUTER JOIN fees \n"
                + "ON student.id=fees.id   WHERE paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"') GROUP BY student.fname,student.names,student.id,fees.id";
        ResultSet rs = handler.execQuery(q);
        try {
            while (rs.next()) {
                String fmame = rs.getString("fname").toUpperCase();
                String name = rs.getString("names").toUpperCase();
                String id_no = rs.getString("id").toUpperCase();
               int vhire=rs.getInt("tt");
               int reg=rs.getInt("ff");

                
                

                list.add(new RegisterController.Member(fmame +"  " +name,  id_no,vhire,reg));

            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(list);
    }

    @FXML
    private void select(ActionEvent event) {
        loadData();
    }
      public static class Member{
        private final SimpleStringProperty name;
        private final SimpleStringProperty id_no;
        private final SimpleIntegerProperty reg;
        private final SimpleIntegerProperty vhire ;
        public Member(String name,  String id_no,int vhire,int reg) {
            this.name = new SimpleStringProperty(name);
            this.id_no = new SimpleStringProperty (id_no);
            this.reg = new SimpleIntegerProperty(reg);
            this.vhire = new SimpleIntegerProperty (vhire);
         }
      
          public String getName() {
            return name.get();
        }

        public String getId_no() {
            return id_no.get();
        }

        public int getVhire() {
            return vhire.get();
        }

        public int getReg() {
            return reg.get();
        }
      
      
      }
}
