/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import icons.DatabaseHandler;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import real.util.LibraryAssistantUtil;
import realt.alert.AlertMaker;


/**
 * FXML Controller class
 *
 * @author kRich
 */
public class FeebalController implements Initializable {
 ObservableList<FeebalController.Member> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> paidCol;
    @FXML
    private TableColumn<Member, String> totalcol;
    @FXML
    private TableColumn<Member, String> balbcol;
    @FXML
    private TableColumn<Member, String> categorycol;
       ViewfeeController t= new ViewfeeController();
    @FXML
    private StackPane rootPane;
    private TableColumn<Member, String> vhirecol;
    private TableColumn<Member, String> regcol;
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
        // TODO
        initCol();
     
    
   
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fmame"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_no"));
        paidCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        totalcol.setCellValueFactory(new PropertyValueFactory<>("fee"));
        balbcol.setCellValueFactory(new PropertyValueFactory<>("bal"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("cat"));
      ;
        
        
    }

   private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();
 id = datfom1.getValue().toString();
  todatee=datto1.getValue().toString();
        DatabaseHandler handler = DatabaseHandler.getInstance();
   
        String q= "SELECT student.fname,student.names,student.id,student.category,\n"
                + "Student.fees,SUM(instalment.amount) as tt, instalment.paydate \n"
                + " FROM student LEFT OUTER JOIN instalment \n"
                + "ON student.id=instalment.id  WHERE instalment.paydate BETWEEN DATE ('"+id+"') AND DATE('"+todatee+"') GROUP BY student.fname,student.names,student.category,student.id,Student.fees";
        ResultSet rs = handler.execQuery(q);
        try {
            while (rs.next()) {
                String fmame = rs.getString("fname").toUpperCase();
                String name = rs.getString("names").toUpperCase();
                String id_no = rs.getString("id").toUpperCase();
                int fees=rs.getInt("fees");
                int amount = rs.getInt("tt");
                int bal=fees-amount;
               String cat = rs.getString("category").toUpperCase();
               int vhire=rs.getInt("tt");
               int reg=rs.getInt("tt");

                
                

                list.add(new FeebalController.Member(fmame +"  " +name,  id_no,  fees,  bal,  amount, cat,vhire,reg));

            }
        } catch (SQLException ex) {
            Logger.getLogger(FeebalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }
   

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData() ;
    }

    @FXML
    private void exportAsPDF(ActionEvent event) {
          List<List> printData = new ArrayList<>();
        String[] headers = {"STUDENT NAME        ","ID NUMBER","CATEGORY","TOTAL","AMOUNT PAID","FEE  BALANCE"};
        printData.add(Arrays.asList(headers));
        for (Member member : list) {
            List<String> row = new ArrayList<>();
            row.add(member.getFmame());
             row.add(member.getId_no());
            row.add(member.getCat());
            row.add(""+member.getFee());
            row.add(""+member.getAmount());
           
            row.add(""+member.getBal());
            
            printData.add(row);
        }
        LibraryAssistantUtil.initPDFExprot(rootPane, contentPane, getStage(), printData); 
        
    }

    @FXML
    private void closeStage(ActionEvent event) {
    }

    @FXML
    private void handleMemberDelete(ActionEvent event) {
    FeebalController.Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No student selected", "Please select a student for deletion.");
            return;
        }
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting student");
        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getFmame() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteBook(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("student deleted", selectedForDeletion.getFmame() +" " + selectedForDeletion.getFmame()+ " was deleted successfully.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getFmame()+" " + selectedForDeletion.getFmame()+ " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
   }

   

    @FXML
    private void handleMemberEdit(ActionEvent event) {
        //Fetch the selected row
        Member selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No student selected selected", "Please select a member for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fees/fees.fxml"));
            Parent parent = loader.load();

            FeesController controller = (FeesController) loader.getController();
            controller.infalteUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit student");
            stage.setScene(new Scene(parent));
             stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            LibraryAssistantUtil.setStageIcon(stage);

            stage.setOnCloseRequest((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            Logger.getLogger(FeebalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void select(ActionEvent event) {
            loadData();
    }
    
     public static class Member{
        private final SimpleStringProperty fmame;
        private final SimpleStringProperty id_no;
        private final SimpleIntegerProperty fee;
        private final SimpleIntegerProperty bal ;
        private final SimpleIntegerProperty amount;
        private final SimpleStringProperty cat;
        private final SimpleIntegerProperty vhire;
        private final SimpleIntegerProperty reg;
       
        
        
     public Member(String fmame,  String id_no, int fee, int bal, int amount,String cat,int vhire,int reg) {
            this.fmame = new SimpleStringProperty(fmame);
            this.id_no = new SimpleStringProperty (id_no);
          
            this.fee = new SimpleIntegerProperty(fee);
            this.bal = new SimpleIntegerProperty (bal);
            
            this.amount = new SimpleIntegerProperty (amount);
              this.cat = new SimpleStringProperty (cat);
              this.vhire=new SimpleIntegerProperty (vhire);
              this.reg=new SimpleIntegerProperty(reg);
     }
        public String getFmame() {
            return fmame.get();
        }

        public String getCat() {
            return cat.get();
        }

        public String getId_no() {
            return id_no.get();
        }

        public int getFee() {
            return fee.get();
        }

        public int getBal() {
            return bal.get();
        }

        public int getVhire() {
            return vhire.get();
        }

        public int getReg() {
            return reg.get();
        }

       

       

        public int getAmount() {
            return amount.get();
        }
        }
     


    
}
