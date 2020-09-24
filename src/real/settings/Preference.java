
package real.settings;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import realt.alert.AlertMaker;
import org.apache.commons.codec.digest.DigestUtils;

public class Preference {

    public static final String CONFIG_FILE = "config2.txt";

 
    String username;
    String password;

  
 public Preference() {
 
        username = "admin";
        setPassword("admin");
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
  

    public String getPassword() {
        return password;
    }
  


    public void setPassword(String password) {
        if (password.length() < 16) {
            this.password = DigestUtils.shaHex(password);
        }else
            this.password = password;
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            Preference preference = new Preference();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);
        } catch (IOException ex) {
            Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Preference getPreference() {
        Gson gson = new Gson();
        Preference preference = new Preference();
        try {
            preference = gson.fromJson(new FileReader(CONFIG_FILE), Preference.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preference;
    }

    public static void writePreferenceToFile(Preference preference) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);

            AlertMaker.showSimpleAlert("Success", "Settings updated");
        } catch (IOException ex) {
            Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showErrorMessage(ex, "Failed", "Cant save configuration file");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
