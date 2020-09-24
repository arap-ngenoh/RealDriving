/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package realdriving;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import real.util.LibraryAssistantUtil;

/**
 *
 * @author kRich
 */
public class RealDriving extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
     
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/expense/l0g.fxml"));
            Parent parent = loader.load();

            Stage stag = new Stage(StageStyle.DECORATED);
            stag.setTitle("please login ----");
            stag.setScene(new Scene(parent));
             stag.initModality(Modality.APPLICATION_MODAL);
            stag.show();
            LibraryAssistantUtil.setStageIcon(stag);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
