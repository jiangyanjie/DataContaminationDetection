package SyslogCDR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBInsert implements Runnable {

    private final String CallingPeerAddress, CalledPeerAddress, SetupTime, ConnectTime, DisconnectTime,DisconnectCause;
    private final boolean IsMissed;
    private final int TypeAppel;
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;

    public DBInsert(String CallingPeerAddress, String CalledPeerAddress,
            String SetupTime, String ConnectTime, String DisconnectTime, boolean IsMissed, int TypeAppel,String DisconnectCause) {
        this.CallingPeerAddress = CallingPeerAddress;
        this.CalledPeerAddress = CalledPeerAddress;
        this.SetupTime = SetupTime;
        this.ConnectTime = ConnectTime;
        this.DisconnectTime = DisconnectTime;
        this.IsMissed = IsMissed;
        this.TypeAppel = TypeAppel;
        this.DisconnectCause = DisconnectCause;
    }

    @Override
    public void run() {
        try {
            //System.err.println("DBThread Runs");
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/cdr_data?user=root&password=root");
            preparedStatement = connect.prepareStatement("insert into  cdr_inbound "
                    + "(CallingPeerAddress,CalledPeerAddress,SetupTime,ConnectTime,"
                    + "DisconnectTime,IsMissed,DateAdded,TypeAppel,DisconnectCause) values (?, ?, TIME_FORMAT(?, '%H:%i:%s.%f'), TIME_FORMAT(?, '%H:%i:%s.%f'), TIME_FORMAT(?, '%H:%i:%s.%f') ,? ,DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s.%f') ,?,?)");
            preparedStatement.setString(1, CallingPeerAddress);
            preparedStatement.setString(2, CalledPeerAddress);
            preparedStatement.setString(3, SetupTime);
            preparedStatement.setString(4, ConnectTime);
            preparedStatement.setString(5, DisconnectTime);
            preparedStatement.setBoolean(6, IsMissed);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date dte = new Date();
            preparedStatement.setString(7, sdf.format(dte));
            preparedStatement.setInt(8, TypeAppel);
            preparedStatement.setInt(9, Integer.parseInt(DisconnectCause,16));
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger(ConcurrentDataInsertTest.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBInsert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
