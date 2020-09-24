/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enqure;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import icons.DatabaseHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import newstudents.NewstudentController;
import real.util.LibraryAssistantUtil;
import realt.alert.AlertMaker;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class EnqureController implements Initializable {
    ObservableList<enquire> data = FXCollections.observableArrayList();
    @FXML
    private DatePicker date;
    @FXML
    private TextField name;
    @FXML
    private TextField contact;
    @FXML
    private TextArea message;
    @FXML
    private JFXButton save;
           DatabaseHandler handler;
    @FXML
    private TableView <enquire>p_display;

    @FXML
    private TableColumn<enquire, String> namecol;
    @FXML
    private TableColumn<enquire, String> pdlcol;
    @FXML
    private TableColumn<enquire, String> tdbcol;
    @FXML
    private TableColumn<enquire, String> eoccol;
    @FXML
    private TableColumn<enquire, String> dlcol;
    @FXML
    private Tab view;

 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            initcol();
       
        tablodoldur();
    }    
 private void tablodoldur()
    {
     data.clear();
    
       
           data = FXCollections.observableArrayList();
          try{
          handler = DatabaseHandler.getInstance();
        
            String SQL="SELECT id, message,person,dat,contact,remarks FROM enquiries"; 
            
            ResultSet rs = handler.execQuery(SQL);
          while (rs.next()) {
                String messag = rs.getString("message").toUpperCase();
                String person = rs.getString("person").toUpperCase();
                String datee = rs.getDate("dat").toString().toUpperCase();
                String phone= rs.getString("contact").toUpperCase();
                String remarks=rs.getString("remarks");
                int id=rs.getInt("id");

                data.add(new EnqureController.enquire(id,messag,person,datee,phone,remarks));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EnqureController.class.getName()).log(Level.SEVERE, null, ex);
        }

        p_display.setItems(data);
   
            }

    @FXML
    private void save(ActionEvent event) {
        String typee= message.getText();
       String person= name.getText();
      String val = contact.getText();
      Boolean flag=typee.isEmpty()||person.isEmpty()||val.isEmpty();
      if(flag){
      
           Alert alert=new  Alert(Alert.AlertType.ERROR);       
            alert.setContentText(" Date, Name and his or her contacts are required ");
            alert.showAndWait();
            alert.close();
      }
      else{
        try {
                  String query = "INSERT INTO enquiries(message,person,dat,contact) VALUES ("
                +"'"+typee+"',"+
              "'"+ person+"',"+
                 "'"+date.getValue()+"',"+
               "'"+val+"'"+
                
                ")";
     handler = DatabaseHandler.getInstance();
              if(  handler.execAction(query)){
               Alert alert=new  Alert(Alert.AlertType.INFORMATION);       
            alert.setContentText("new enquiry added successfuly ");
            alert.showAndWait();
            alert.close();
           }
        else{
        Alert alert=new  Alert(Alert.AlertType.ERROR);
            alert.setContentText("error   occurred ... please try again");
            alert.showAndWait();
             alert.close();
        }
    }
        catch(Exception e){
        
        }
    }
    }

    @FXML
    private void remarks(ActionEvent event) {
        EnqureController.enquire selectedForEdit = p_display.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No enquiry  selected", "Please select a enquiry for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/enqure/remarks.fxml"));
            Parent parent = loader.load();

            RemarksController controller = (RemarksController) loader.getController();
            controller.infalteUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Add  remarks here");
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

    private void initcol() {
   
 namecol.setCellValueFactory(new PropertyValueFactory<>("message"));
                pdlcol.setCellValueFactory(new PropertyValueFactory<>("person"));
                tdbcol.setCellValueFactory(new PropertyValueFactory<>("date"));
                eoccol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                dlcol.setCellValueFactory(new PropertyValueFactory<>("remarks"));    
    }
public void refresh(){
    view.isSelected();
        initcol();
        tablodoldur();
}
    @FXML
    private void handleRefresh(ActionEvent event) {
          
       refresh();
    }

    @FXML
    private void handleRefresh(Event event) {
         refresh();

    }

    @FXML
    private void edit(ActionEvent event) {
         EnqureController.enquire selectedForEdit = p_display.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No enquiry  selected", "Please select a enquiry for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/enqure/edit.fxml"));
            Parent parent = loader.load();

            EditController controller = (EditController) loader.getController();
            controller.infalteUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit  here");
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
    public class enquire{
        int id;
    String message;
     String person;
      String date;
         String phone;
         String remarks;

        public enquire( int id,String message, String person, String date, String phone, String remarks) {
            this.message = message;
            this.person = person;
            this.date = date;
            this.phone = phone;
            this.remarks = remarks;
            this.id=id;
        }

        public enquire(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public String getPerson() {
            return person;
        }

        public String getDate() {
            return date;
        }

        public String getPhone() {
            return phone;
        }

        public String getRemarks() {
            return remarks;
        }
    
    }
}
