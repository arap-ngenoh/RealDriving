
package newstudents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kRich
 */
public class Connector {
    Connection con=null;
    PreparedStatement prepared=null;
    
    private final static String SERVER="jdbc:mysql://localhost";
    private static final String USER="root";
    private static final String PASSWORD="Coder1234";
    
    public static void main(String [] args){
        new Connector().connectMe();
    }

    public Connection connectMe() {
        try {
            con=DriverManager.getConnection(SERVER,USER,PASSWORD);
            
            Statement state=con.createStatement();
            state.execute("USE realdriving");
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
       
    }
    
    
}
