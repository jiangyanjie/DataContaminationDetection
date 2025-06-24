package ru.arlen.dao;

import ru.arlen.entity.LocationInfo;
import ru.arlen.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class DAOImpl implements DAO {
    private static String JDBC_PROP = "jdbc";
    private static String URL = "url";
    private static String USER = "user";
    private static String PASSWORD = "pass";

    private ResourceBundle propFile;
    private Properties prop;
    private Connection connect;

    public DAOImpl() {
        init();
    }

    private void init() {
        try {
            propFile = ResourceBundle.getBundle(JDBC_PROP, Locale.ENGLISH);
            connect = DriverManager.getConnection(
                    propFile.getString(URL),
                    propFile.getString(USER),
                    propFile.getString(PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAuth(User user) throws SQLException {
        PreparedStatement prepSt = null;
        ResultSet rs = null;

        try {
//            prepSt = connect.prepareStatement("select id from mytable where id=?");
            prepSt = connect.prepareStatement("select distinct app_id from T_GTBT_USAGE_TRACKING where app_id =?");
            prepSt.setInt(1, user.getStid());

            rs = prepSt.executeQuery();
            return rs.next();
        } finally {
            try {
                if (prepSt != null) {
                    prepSt.close();
                }
            } catch (SQLException sqle) {
                //NOP
            }
            rs = null;
            prepSt = null;
        }
    }

    @Override
    public int getAppId(User user) throws SQLException {
        PreparedStatement prepSt = null;
        ResultSet rs = null;

        try {
//            prepSt = connect.prepareStatement("select id from mytable where id=?");
            prepSt = connect.prepareStatement("select distinct app_id from T_GTBT_USAGE_TRACKING where app_id =?");
            prepSt.setInt(1, user.getStid());

            rs = prepSt.executeQuery();
            return rs.getInt(1);
        } finally {
            try {
                if (prepSt != null) {
                    prepSt.close();
                }
            } catch (SQLException sqle) {
                //NOP
            }
            rs = null;
            prepSt = null;
        }
    }

    @Override
    public LocationInfo getLocationInfo(int recordId) {
        PreparedStatement prepSt = null;
        ResultSet rs = null;
        LocationInfo locationInfo = new LocationInfo();
        String query = "select tracking_id, record_number, latitude, longitude, speed, heading, elevation, aged_status, ehpe from t_vtm_location_info where RECORD_NUMBER = ?";
        try {
            prepSt = connect.prepareStatement(query);
            prepSt.setInt(1, recordId);
            rs = prepSt.executeQuery();

            if (rs.next()) {
                locationInfo.setTrackingId(rs.getBigDecimal(1));
                locationInfo.setRecordNumber(rs.getInt(2));
                locationInfo.setLatitude(rs.getInt(3));
                locationInfo.setLongitude(rs.getInt(4));
                locationInfo.setSpeed(rs.getInt(5));
                locationInfo.setHeading(rs.getInt(6));
                locationInfo.setElevation(rs.getInt(7));
                locationInfo.setAgedStatus(rs.getString(8));
                locationInfo.setEhpe(rs.getInt(9));
            }
            /*System.out.println("Row = " + rs.getRow());
            System.out.println("tracking_id=" + rs.getBigDecimal(1));
            System.out.println("record_number=" + rs.getInt(2));
            System.out.println("latitude=" + rs.getInt(3));
            System.out.println("longitude=" + rs.getInt(4));
            System.out.println("speed=" + rs.getInt(5));
            System.out.println("heading=" + rs.getInt(6));
            System.out.println("elevation=" + rs.getInt(7));
            System.out.println("aged_status=" + rs.getString(8));
            System.out.println("ehpe=" + rs.getInt(9));
            System.out.println();*/
        } catch (SQLException sqle) {
            //NOP
        } finally {
            try {
                if (prepSt != null) {
                    prepSt.close();
                }
            } catch (SQLException sqle) {
                //NOP
            }
            rs = null;
            prepSt = null;
        }
        return locationInfo;
    }
}

/*class w {

    private static String JDBC_PROP = "jdbc";
    private static String URL = "url";
    private static String USER = "user";
    private static String PASSWORD = "pass";

    private static ResourceBundle propFile;
    private static Properties prop;
    private static Connection connect;

    public static void main(String[] args) {


        try {
            propFile = ResourceBundle.getBundle(JDBC_PROP, Locale.ENGLISH);
            connect = DriverManager.getConnection(
                    propFile.getString(URL),
                    propFile.getString(USER),
                    propFile.getString(PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement prepSt = null;
        ResultSet rs = null;
        String query = "select tracking_id, record_number, latitude,longitude, speed,heading,elevation,aged_status, ehpe from t_vtm_location_info where tracking_id = ?";

        try {
            prepSt = connect.prepareStatement(query);
            prepSt.setBigDecimal(1, new BigDecimal(5837 + "0000000000000000"));
            rs = prepSt.executeQuery();

            while (rs.next()) {
                System.out.println("Row = " + rs.getRow());
                System.out.println("tracking_id=" + rs.getBigDecimal(1));
                System.out.println("record_number=" + rs.getInt(2));
                System.out.println("latitude=" + rs.getInt(3));
                System.out.println("longitude=" + rs.getInt(4));
                System.out.println("speed=" + rs.getInt(5));
                System.out.println("heading=" + rs.getInt(6));
                System.out.println("elevation=" + rs.getInt(7));
                System.out.println("aged_status=" + rs.getString(8));
                System.out.println("ehpe=" + rs.getInt(9));
                System.out.println();
            }
        } catch (SQLException sqle) {
            //NOP
        } finally {
            try {
                if (prepSt != null) {
                    prepSt.close();
                }
            } catch (SQLException sqle) {
                //NOP
            }
            rs = null;
            prepSt = null;
        }
    }
}*/


