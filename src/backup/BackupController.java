/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author kRich
 */
public class BackupController implements Initializable {

    @FXML
    private TextField backuppath;
    @FXML
    private JFXButton browserbpath;
    @FXML
    private JFXButton backup;
    @FXML
    private Label backupstatus;
    @FXML
    private TextField restorepath;
    @FXML
    private JFXButton browserestore;
    @FXML
    private Label restorestatus;
    @FXML
    private JFXButton restore;

    String path=null;
    String filename;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void browserbpath(ActionEvent event) {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(fc);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        try {
            File f =fc.getSelectedFile();
            path = f.getAbsolutePath();
            path = path.replace('\\', '/');
            path = path + "_" + date + ".sql";
            backuppath.setText(path);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backup(ActionEvent event) {
        Process p=null;
        try {
            Runtime runtime = Runtime.getRuntime();
            p=runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump.exe -uroot -pCoder1234 realdriving -r"+path);
            
            int processComplete = p.waitFor();
            if (processComplete==0) {
                backupstatus.setText("Backup Created Succuss");
            }else{
                backupstatus.setText("Can't Create backup");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void browserestore(ActionEvent event) {
        JFileChooser fc = new JFileChooser();
       fc.showOpenDialog(fc);
        try {
            File f = fc.getSelectedFile();
            path = f.getAbsolutePath();
            filename = path.replace('\\', '/');
            
            restorepath.setText(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void restore(ActionEvent event) {
         String dbUserName = "root";// username
        String dbPassword = "Coder1234";//Password
        
        String[] restoreCmd = new String[]{"C:/Program Files/MySQL/MySQL Server 5.7/bin/mysql.exe ", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source " + filename};
        Process runtimProcess;
        try {
            runtimProcess = Runtime.getRuntime().exec(restoreCmd);
            int proceCom = runtimProcess.waitFor();
            
            if (proceCom==0) {
                restorestatus.setText("Restored Succuss");
            }else{
                restorestatus.setText("Restored Succuss");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
