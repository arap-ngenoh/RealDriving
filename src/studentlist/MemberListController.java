package studentlist;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import newstudents.NewstudentController;
import newstudents.UpdateController;
import icons.DatabaseHandler;
import javafx.scene.control.SplitPane;

import real.util.LibraryAssistantUtil;
import realt.alert.AlertMaker;

public class MemberListController implements Initializable {

    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> regcol;
   
    @FXML
    private StackPane rootPane;
    @FXML
    private SplitPane contentPane;
    @FXML
    private TableColumn<Member, String> dlcol;
    @FXML
    private TableColumn<Member, String> tdbcol;
    @FXML
    private TableColumn<Member, String> pdlcol;
    @FXML
    private TableColumn<Member, String> categorycol;
    @FXML
    private JFXButton print;
    @FXML
    private TableColumn<Member, String> datecol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fmame"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("idno"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dlcol.setCellValueFactory(new PropertyValueFactory<>("dl"));
        tdbcol.setCellValueFactory(new PropertyValueFactory<>("tdb"));
        pdlcol.setCellValueFactory(new PropertyValueFactory<>("pdl"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        regcol.setCellValueFactory(new PropertyValueFactory<>("reg"));
        
    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = " select student.regnum,student.id,student.fname,student.names,student.phone,student.tdbnumber,student.pdlnumber,student.dlnumber,student.category,fees.paydate from student left outer join fees on student.id=fees.id GROUP BY student.id ";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String fmame = rs.getString("fname").toUpperCase();
                String name = rs.getString("names").toUpperCase();
                String phone = rs.getString("phone").toUpperCase();
                String idno = rs.getString("id").toUpperCase();
                String pdl = rs.getString("pdlnumber").toUpperCase();
                String tdb = rs.getString("tdbnumber").toUpperCase();
                String dl = rs.getString("dlnumber").toUpperCase();
                String amount = rs.getString("category");
                String date = rs.getString("paydate");
                String regno = rs.getString("regnum");

                list.add(new Member(fmame+"   "+name, name, idno,  phone,  pdl,  tdb,  dl,  amount,date,regno));

            }
        } catch (SQLException ex) {
            Logger.getLogger(NewstudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }

  @FXML
   private void handleMemberDelete(ActionEvent event) {
        //Fetch the selected row
        MemberListController.Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No student selected", "Please select a student for deletion.");
            return;
        }
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting student");
        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getFmame() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteMember(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("student deleted", selectedForDeletion.getFmame() +" " + selectedForDeletion.getNames()+ " was deleted successfully.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getFmame()+" " + selectedForDeletion.getNames()+ " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
   }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void handleMemberEdit(ActionEvent event) {
        //Fetch the selected row
        Member selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newstudents/update.fxml"));
            Parent parent = loader.load();

            UpdateController controller = (UpdateController) loader.getController();
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
            Logger.getLogger(NewstudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exportAsPDF(ActionEvent event) {

        List<List> printData = new ArrayList<>();
        String[] headers = {"STUDENT NAME             ", "ID NUMBER", "CONTACTS    ", "PDL Number "," CATEGORY" , " TDB Number ","DL Number"};
        //String [] footers={};
        printData.add(Arrays.asList(headers));
        for (Member member : list) {
            List<String> row = new ArrayList<>();
      row.add(member.getFmame()+" "+member.getNames().toUpperCase());
            row.add(member.getIdno().toUpperCase());
            row.add(member.getPhone().toUpperCase());
            row.add(member.getPdl().toUpperCase());
             row.add(member.getAmount().toUpperCase());
            row.add(member.getTdb().toUpperCase());
            row.add(member.getDl().toUpperCase());

            printData.add(row);
        }
        LibraryAssistantUtil.initPDFExprot(rootPane, contentPane, getStage(), printData);
    }

    private void closeStage(ActionEvent event) {
        getStage().close();
    }

   

  public static class Member{
        private final SimpleStringProperty fmame;
        private final SimpleStringProperty names;
        private final SimpleStringProperty idno;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty pdl;
        private final SimpleStringProperty tdb;
        private final SimpleStringProperty dl;
        private final SimpleStringProperty amount;
      private final SimpleStringProperty date;
      private final SimpleStringProperty reg;
        public Member(String fmame, String names, String idno, String phone, String pdl, String tdb, String dl, String amount ,String date,String reg) {
            this.fmame = new SimpleStringProperty(fmame);
            this.names = new SimpleStringProperty (names);
            this.idno = new SimpleStringProperty (idno);
            this.phone = new SimpleStringProperty(phone);
            this.pdl = new SimpleStringProperty (pdl);
            this.tdb = new SimpleStringProperty (tdb);
            this.dl = new SimpleStringProperty  (dl);
            this.amount = new SimpleStringProperty (amount);
            this.date = new SimpleStringProperty (date);
            this.reg = new SimpleStringProperty (reg);
        }

        public String getFmame() {
            return fmame.get();
        }

        public String getNames() {
            return names.get();
        }

        public String getIdno() {
            return idno.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getPdl() {
            return pdl.get();
        }

        public String getTdb() {
            return tdb.get();
        }

        public String getDl() {
            return dl.get();
        }

        public String getAmount() {
            return amount.get();
        }
         public String getDate() {
            return date.get();
        }
         public String getReg() {
            return reg.get();
        }
        }

}
