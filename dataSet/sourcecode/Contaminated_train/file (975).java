package DBModification;

import SyslogCDR.ConcurrentDataInsertTest;
import SyslogCDR.DBInsert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvertDestCauseToInt {

    static int i;
    private String DestCause;
    private int ID;
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private List<String[]> DBGet = new ArrayList<String[]>();

    public static void main(String[] args) {
        ConvertDestCauseToInt te = new ConvertDestCauseToInt();
        te.getVal();
    }

    public void getVal() {
        try {
            //System.err.println("DBThread Runs");
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/cdr_data?user=root&password=root");
            preparedStatement = connect.prepareStatement("select ID_CDR_Syslog, DisconnectCause from cdr_inbound");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ID = rs.getInt("ID_CDR_Syslog");
                DestCause = rs.getString("DisconnectCause");
                if (!"null".equals(DestCause) &&  ID > 2503) {
                    String st = String.valueOf(Integer.parseInt(DestCause, 16));
                    //System.err.println(ID + " : " + DestCause + " ----- > " + st);
                    insertConverted(ID, st);
                } else {
                    System.err.println("null");
                }
                i = ID;
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ConvertDestCauseToInt.class.getName()).log(Level.SEVERE, String.valueOf(i), e);
        }
    }

    public void insertConverted(int ID, String Cause) {
        try {
            //System.err.println("DBThread Runs");
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/cdr_data?user=root&password=root");
            preparedStatement = connect.prepareStatement("UPDATE cdr_data.cdr_inbound SET DisconnectCause=? WHERE ID_CDR_Syslog=?;");
            preparedStatement.setString(1, Cause);
            preparedStatement.setInt(2, ID);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ConvertDestCauseToInt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
