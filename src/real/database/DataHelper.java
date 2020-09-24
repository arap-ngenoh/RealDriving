package real.database;

import icons.DatabaseHandler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import real.data.model.Member;
import real.data.model.Payment;

/**
 *
 * @author afsal
 */
public class DataHelper {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());

  

    public static boolean insertNewMember(Member member) throws ClassNotFoundException {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
            "INSERT INTO student(fname,names,id,phone,pdlnumber,tdbnumber,dlnumber,category,fees) VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setString(1, member.getFname());
            statement.setString(2, member.getNames());
            statement.setString(3, member.getIdno());
            statement.setString(4, member.getHone());
            statement.setString(5, member.getPdl());
            statement.setString(6, member.getTdb());
            statement.setString(7, member.getDl());
            statement.setString(8, member.getCategory());
            statement.setInt(9, member.getFecatt());

            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isMemberExists0(String id) throws ClassNotFoundException {
        try {
            String checkstmt = "SELECT COUNT(*) FROM fees WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean isMemberExists(String id) throws ClassNotFoundException {
        try {
            String checkstmt = "SELECT COUNT(*) FROM student WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count == 1);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }
    
     public static boolean isMemberExists1(String id) throws ClassNotFoundException {
        try {
            String checkstmt = "SELECT COUNT(*) FROM fees WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }


    public static void wipeTable(String tableName) throws ClassNotFoundException {
        try {
            Statement statement = DatabaseHandler.getInstance().getConnection().createStatement();
            statement.execute("DELETE FROM " + tableName + " WHERE TRUE");
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }
    
      public static boolean  makepayment(Payment payment) throws ClassNotFoundException{
          
          try{
               PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
            "INSERT INTO fees( dat,id, amount) VALUES(?,?,?)");
            statement.setString(1, payment.getAmount());
            statement.setString(2, payment.getId());
             //statement.setDate(3, payment.getDate());
            return statement.executeUpdate() > 0;

          }catch(SQLException ex){
          LOGGER.log(Level.ERROR, "{}", ex);

              
          }
          return false;
      }

   
}
