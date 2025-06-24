package com.travelman.data.service;

import com.travelman.action.report.IdealTimeReportAction;
import com.travelman.domain.Bill;
import com.travelman.domain.Payment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.travelman.action.report.TripReportAction;
import com.travelman.data.connection.DBConnection;
import com.travelman.domain.Coupon;
import com.travelman.domain.Device;
import com.travelman.domain.Email;
import com.travelman.domain.Feedback;
import com.travelman.domain.Offer;
import com.travelman.domain.Plan;
import com.travelman.domain.Profile;
import com.travelman.domain.Report;
import com.travelman.domain.Sms;
import com.travelman.domain.SmsPlan;
import com.travelman.domain.Tax;
import com.travelman.domain.TrackData;
import com.travelman.domain.User;
import com.travelman.domain.Vehicle;
import java.util.*;
import java.text.*;
import javax.sound.midi.SysexMessage;
import org.apache.log4j.Logger;

public class DataService {
    private Logger log=Logger.getLogger(DataService.class);

    public List<Integer> getUseSubUserList(int userid, int uprofile) {
        String queryINSERTMSISDN = "";
        List<Integer> list = new ArrayList<Integer>();
        if (uprofile == 3) {
            queryINSERTMSISDN = "select userid from user";
        } else {
            queryINSERTMSISDN = "select userid from user where ownerid='" + userid + "' or userid='" + userid + "'";
        }
        log.info(queryINSERTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryINSERTMSISDN);
            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getInt("userid"));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public String selectDeviceForEdit(long deviceid, Device device) {
        String actionResult = "";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery("select * from device where deviceid = '" + deviceid + "'");
            if (m_ResultSet.next()) {
                device.setUserid(m_ResultSet.getInt("userid"));
                device.setDeviceId(m_ResultSet.getLong("deviceid"));
                device.setDtype(m_ResultSet.getString("dtype"));
                device.setDiemi(m_ResultSet.getString("diemi"));
                device.setDsim_num(m_ResultSet.getString("dsim_num"));
                actionResult = "ok";
            } else {
                log.info("if fails -->>selectDeviceForEdit");
                actionResult = "SQLException";
            }
        } catch (SQLException e) {
            actionResult = "SQLException";
            log.info(e + "selectDeviceForEdit");
        } finally {
            try {
                if (m_Connection != null) {
                    m_Connection.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
            } catch (SQLException ee) {
                actionResult = "SQLException";
            }
        }
        return actionResult;
    }

    public List<Long> getDeviceIdList() {
        List<Long> list_DeviceId = new ArrayList<Long>();
        String queryLISTDEVICE = "select deviceid from device";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            int i = 0;
            while (m_ResultSet.next()) {

                list_DeviceId.add(m_ResultSet.getLong("deviceid"));

                log.info("Deviceid : " + list_DeviceId.get(i));
                i++;
            }
        } catch (SQLException e) {
        }
        return list_DeviceId;
    }

    public int getMaxIdVehicle() {
        int id = 1;
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery("select max(vehicleid) from  vehicle");
            if (m_ResultSet.next()) {
                id = m_ResultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
        }
        return id;
    }

    public String validateDevice(String dsim_num, long deviceid, int userid, User user) {
        String actionResult = "";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery("select deviceid from device where dsim_num='"
                    + dsim_num + "'");
            if (m_ResultSet.next()) {

                actionResult = "sim";
            } else {
                try {

                    m_ResultSet = m_Statement.executeQuery("select deviceid from device where deviceid='" + deviceid + "'");
                    log.info(m_ResultSet + "resultset");
                    if (m_ResultSet.next()) {

                        actionResult = "deviceid";
                    } else {
                        actionResult = "ok";
                    }
                } catch (SQLException se) {
                    actionResult = "SQLException";
                }

            }

        } catch (SQLException e) {
            actionResult = "SQLException";
        }
        log.info(actionResult + "  in data Service validate device");
        return actionResult;
    }
    private static Connection m_Connection = null;
    /**
     * 
     */
    private ResultSet m_ResultSet = null;
    /**
     * 
     */
    private Statement m_Statement = null;
    /**
     * 
     */
    private DBConnection dbConnection = DBConnection.getConnectionInstance();

    /**
     * @return the dbConnection
     */
    public DBConnection getDbConnection() {
        return dbConnection;
    }

    /**
     * @param dbConnection
     *            the dbConnection to set
     */
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    Calendar cal = Calendar.getInstance();
    int y = cal.get(Calendar.YEAR);
    int mm = cal.get(Calendar.MONTH);
    int d = cal.get(Calendar.DAY_OF_MONTH);
    int m = ++mm;

    public String createDevice(Device device) {
        String actionResult = "";
        try {
            String str = "insert into device(deviceid,dtype,diemi,dreg_date,dsim_num,userid) values"
                    + "("
                    + "'" + device.getDeviceId() + "',"
                    + "'" + device.getDtype() + "',"
                    + "'" + device.getDiemi() + "',"
                    //   + "'" + device.getDreg_date() + "',"
                    + "'" + y + '/' + m + '/' + d + "',"
                    + "'" + device.getDsim_num() + "',"
                    + "'" + device.getUserid() + "'"
                    + ")";
            int i = m_Statement.executeUpdate(str);
            log.info(m_ResultSet + ".................");
            if (i != 0) {
                actionResult = "success";
            } else {
                actionResult = "SQLException";
            }

        } catch (SQLException e) {
            log.info(e + "Device*******&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return actionResult;
    }

    public void sendSms(Sms sms) {

        String queryINSERTMSISDN = "insert into sms values ('"
                + sms.getMobileno() + "','" + sms.getTime() + "')";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryINSERTMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void updateUser(User user) {
        // TODO Auto-generated method stub
    }

    public void removeUser(User user) {
        // TODO Auto-generated method stub
    }

    public List<User> listUser(User user) {

        List<User> list = null;

        String queryLISTMSISDN = "SELECT * from user ";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                if (list == null) {
                    list = new ArrayList<User>();
                }

                user.setFname(m_ResultSet.getString(2));
                user.setLname(m_ResultSet.getString(3));
                user.setHaddress(m_ResultSet.getString(4));
                user.setBaddress(m_ResultSet.getString(5));
                user.setUtype(m_ResultSet.getString(6));
                user.setUmobile(m_ResultSet.getString(7));
                user.setUemail(m_ResultSet.getString(7));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
        return list;

    }

    public int getMaxIdDevice() {
        int id = 1;
        String queryMAXID = "select max(deviceId) from device";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryMAXID);
            if (m_ResultSet.next()) {

                id = m_ResultSet.getInt(1) + 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return id;

    }

    public String updateDevice(Device device) {

        String actionResult = "";
        String queryUPDATEMSISDN = "update device set userid = '" + device.getUserid() + "',dtype = '" + device.getDtype() + "',diemi = '" + device.getDiemi() + "'"
                + ",dsim_num = '" + device.getDsim_num() + "' where deviceid = '" + device.getDeviceId() + "'"
                + "";
        log.info(queryUPDATEMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_Statement.executeUpdate(queryUPDATEMSISDN);
            actionResult = "success";

        } catch (SQLException e) {
            actionResult = "SQLException";
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return actionResult;
    }

    public List<Device> listDevice(int userid, int uprofile) {
        List<Device> device_list = new ArrayList<Device>();
        String queryLISTDEVICE = "";
        if (uprofile == 3) {
            queryLISTDEVICE = "select * from device";
        } else {
            queryLISTDEVICE = "select * from device where userid in(select userid from user where userid='" + userid + "' or ownerid='" + userid + "')";
        }
        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                Device device = new Device();
                device.setDeviceId(m_ResultSet.getLong("deviceid"));
                device.setDtype(m_ResultSet.getString("dtype"));
                device.setDprotocol(m_ResultSet.getString("dprotocol"));
                device.setDiemi(m_ResultSet.getString("diemi"));
                device.setDreg_date(m_ResultSet.getDate("dreg_date"));
                device.setDsim_num(m_ResultSet.getString("dsim_num"));
                device.setUserid(m_ResultSet.getInt("userid"));
                device_list.add(device);
            }

        } catch (SQLException e) {
            device_list = null;
            log.info(e);
        }

        return device_list;
    }

    public List<Device> detailDeleteDevice(long deviceId) {
        List<Device> device_list = new ArrayList<Device>();

        String queryLISTDEVICE = "select * from device where deviceid='" + deviceId + "'";

        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                Device device = new Device();
                device.setDeviceId(m_ResultSet.getLong("deviceid"));
                device.setDtype(m_ResultSet.getString("dtype"));
                device.setDprotocol(m_ResultSet.getString("dprotocol"));
                device.setDiemi(m_ResultSet.getString("diemi"));
                device.setDreg_date(m_ResultSet.getDate("dreg_date"));
                device.setDsim_num(m_ResultSet.getString("dsim_num"));
                device.setUserid(m_ResultSet.getInt("userid"));
                device_list.add(device);
            }

        } catch (SQLException e) {
            device_list = null;
            log.info(e);
        }

        return device_list;
    }

    public void editDevice(Device device) {
        String queryEDITMSISDN = "update device set dtype='"
                + device.getDtype() + "', dprotocol='" + device.getDprotocol()
                + "', diemi='" + device.getDiemi() + "', dreg_date='"
                + device.getDreg_date() + "', dsim_num='"
                + device.getDsim_num() + "' where deviceId=111 ";

        /*
         * String queryUPDATEMSISDN =
         * "update trackman.device set dtype='qq',dprotocol='qq'," +
         * "diemi='qqa',dreg_date='qqa',dsim_num='qqa',dowener='qq',dfleet_id='qq',"
         * + "duser_id='qq' where deviceId=111 ";
         */
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryEDITMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String removeDevice(long deviceid) {

        String actionResult = "";
        String queryREMOVEMSISDN = "delete from device where deviceid='" + deviceid + "' "
                + "and deviceid not in (select deviceid from vehicle)";


        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryREMOVEMSISDN);
            log.info(i + "<<<<<<<<<<<<<<delete device>>>>>>>>>>>>>>>>");
            if (i != 0) {
                actionResult = "delete";
            } else {
                actionResult = "deviceused";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return actionResult;

    }

    public void sendEmail(Email email) {
        String queryINSERTMSISDN = "insert into email values ('"
                + email.getRtype() + "','" + email.getSend_interval() + "','"
                + email.getReport_for_days() + "','" + email.getRec_email()
                + "','" + email.getSend_time() + "','" + email.getVehicleid()
                + "')";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_Statement.executeUpdate(queryINSERTMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void createProfile(Profile profile) {
        String queryINSERTMSISDN = "insert into user_profile values ('"
                + profile.getProfileId()
                + "','"
                + profile.getPname()
                + "','"
                + profile.getPdiscription() + "')";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryINSERTMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<Profile> listProfile(Profile profile) {

        List<Profile> profileList = new ArrayList<Profile>();
        Profile profileObj = null;
        String queryLISTMSISDN = "SELECT * from user_profile ";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                profileObj = new Profile();
                profileObj.setProfileId(m_ResultSet.getLong(1));
                profileObj.setPname(m_ResultSet.getString(2));
                profileObj.setPdiscription(m_ResultSet.getString(3));

                profileList.add(profileObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return profileList;
    }

    public long getMaxId() {
        long id = 1;
        String queryMAXID = "select max(profile_id) from user_profile";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryMAXID);
            if (m_ResultSet.next()) {

                id = m_ResultSet.getLong(1) + 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }

    public void sendFeedback(Feedback feedback) {
        String queryINSERTMSISDN = "insert into feedback values ('"
                + feedback.getSubject() + "','" + feedback.getBody() + "')";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryINSERTMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<Integer> getVehicleId() {

        List<Integer> vehicleIdList = new ArrayList();
        // Vehicle vehicleObj=null;

        String queryLISTMSISDN = "SELECT vehicleid from vehicle ";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                vehicleIdList.add(m_ResultSet.getInt(1));

                /*
                 * vehicleObj=new Vehicle();
                 * vehicleObj.setVehicleid(m_ResultSet.getInt(1));
                 * vehicleIdList.add(vehicleObj);
                 */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return vehicleIdList;

    }

    /**
     * 
     * @param userId
     * @param password
     * @return
     */
    /*
     * public static User getUserInfo(String userId,String password) { String
     * querygetUser =
     * "SELECT * from trackman.user where user_id='"+userId+"' and password='"
     * +password+"'";
     * 
     * User user = null; ResultSet resultSet = null;
     * 
     * try { PreparedStatement ps = m_Connection.prepareStatement(querygetUser);
     * resultSet = ps.executeQuery(); if(resultSet.next()) { //valid user.
     * return ""; } else { //invalid user. return ""; }
     * 
     * return user;
     * 
     * } catch (SQLException e) { e.printStackTrace(); }finally { try { if
     * (resultSet != null) resultSet.close(); if (resultSet != null)
     * resultSet.close(); if (resultSet != null) resultSet.close(); } catch
     * (SQLException ex) { ex.printStackTrace(); } } }
     */
    /**
     * 
     * @param user
     */
    public void createUser(User user) {

        String queryCreateUser = "insert into user values ('"
                + user.getUserId() + "','" + user.getFname() + "','"
                + user.getLname() + "','" + user.getBaddress() + "','"
                + user.getHaddress() + "','" + user.getUtype() + "','"
                + user.getUmobile() + "','" + user.getUemail() + "','"
                + user.getPassword() + "','" + user.getUprofile() + "','"
                + user.getOwnerid() + "','" + user.getActive() + "','"
                + user.getAdmin_id() + "')";
        log.info("************************" + queryCreateUser);

        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryCreateUser);
            log.info(i + "          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 
     * 
     * @param userid
     * @param password
     * @return
     */
    public User authenticateUser(String uemail, String password) {
        log.info("authenticateUser() of DataService Class");

        String queryFindUser = "select * from user where uemail='" + uemail
                + "' and password='" + password + "'";
        // log.info(queryFindUser);
        User user = null;
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryFindUser);
            if (m_ResultSet.next()) {
                // Login valid User.
                user = new User();

                user.setUserId(m_ResultSet.getInt("userid"));
                user.setLname(m_ResultSet.getString("lname"));
                user.setFname(m_ResultSet.getString("fname"));
                user.setBaddress(m_ResultSet.getString("baddress"));
                user.setHaddress(m_ResultSet.getString("haddress"));
                user.setUtype(m_ResultSet.getString("utype"));
                user.setUmobile(m_ResultSet.getString("umobile"));
                user.setUemail(m_ResultSet.getString("uemail"));
                user.setPassword(m_ResultSet.getString("password"));
                user.setUprofile(m_ResultSet.getInt("uprofile"));
                user.setOwnerid(m_ResultSet.getInt("ownerid"));
                user.setActive(m_ResultSet.getInt("active"));
                user.setAdmin_id(m_ResultSet.getInt("admin_id"));

            }  // if finish here.
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return user;

    }

    /*
     * public User createUser(int userid) { User user = null; try{
     * 
     * String str = "select * from user where userid='"+userid+"'"; m_Connection
     * = getDbConnection().getConnection(); m_Statement =
     * m_Connection.createStatement(); m_ResultSet =
     * m_Statement.executeQuery(str); user = new User(); if(m_ResultSet.next())
     * { user.setUserId(m_ResultSet.getInt("userid"));
     * user.setLname(m_ResultSet.getString("lname"));
     * user.setFname(m_ResultSet.getString("fname"));
     * user.setBaddress(m_ResultSet.getString("baddress"));
     * user.setHaddress(m_ResultSet.getString("haddress"));
     * user.setUtype(m_ResultSet.getString("utype"));
     * user.setUmobile(m_ResultSet.getString("umobile"));
     * user.setUemail(m_ResultSet.getString("uemail"));
     * user.setPassword(m_ResultSet.getString("password"));
     * user.setUprofile(m_ResultSet.getInt("uprofile"));
     * user.setOwnerid(m_ResultSet.getInt("ownerid"));
     * user.setActive(m_ResultSet.getInt("active"));
     * 
     * List<Vehicle> vehicle_list = user.getVehicle_list(); vehicle_list =
     * addVehicle(userid); user.setVehicle_list(vehicle_list);
     */
    /**
     * Add , Sub users to this user in List<User>
     */
    /*
     * List<User> user_list = user.getUser_list(); user_list =
     * addSubUsers(userid); user.setUser_list(user_list); } else { user = null;
     * }
     * 
     * }catch(SQLException e) {
     * 
     * } return user; } public List<User> addSubUsers(int userid) { List<User>
     * list = new ArrayList<User>(); try { String str =
     * "select userid from user where ownerid='"+userid+"'"; m_Connection =
     * getDbConnection().getConnection(); m_Statement =
     * m_Connection.createStatement(); m_ResultSet =
     * m_Statement.executeQuery(str); while(m_ResultSet.next()) { int
     * userid_0f_subUser = m_ResultSet.getInt("userid"); User subUser =
     * createUser(userid_0f_subUser); list.add(subUser); } }catch(SQLException
     * e) { log.info(e); } return list; }
     * 
     * public List<Vehicle> addVehicle(int userid) { List<Vehicle> list = new
     * ArrayList<Vehicle>(); try { String str =
     * "select * from vehicle where userid='"+userid+"'"; m_Connection =
     * getDbConnection().getConnection(); m_Statement =
     * m_Connection.createStatement(); m_ResultSet =
     * m_Statement.executeQuery(str); while(m_ResultSet.next()) { Vehicle
     * vehicle = new Vehicle();
     * vehicle.setVehicleId(m_ResultSet.getInt("vehicleid"));
     * vehicle.setvRegistrationNum(m_ResultSet
     * .getString("vregisteration_num"));
     * vehicle.setRegistration_date(m_ResultSet .getDate("registration_date"));
     * vehicle.setVmake(m_ResultSet.getString("vmake"));
     * vehicle.setVmodel(m_ResultSet.getString("vmodel"));
     * vehicle.setVfuel_type(m_ResultSet.getString("vfuel_type"));
     * vehicle.setFleet(m_ResultSet.getString("fleet"));
     * vehicle.setUserid(m_ResultSet.getInt("userid"));
     * 
     * Device device = addDevice(vehicle); vehicle.setDevice(device);
     * list.add(vehicle); }
     * 
     * }catch(SQLException e){
     * 
     * } return list; } public Device addDevice(Vehicle vehicle) { Device device
     * = vehicle.getDevice(); try{ String str =
     * "select * from device where vehicleid='"+vehicle.getVehicleId()+"'";
     * m_Connection = getDbConnection().getConnection(); m_Statement =
     * m_Connection.createStatement(); m_ResultSet =
     * m_Statement.executeQuery(str); if(m_ResultSet.next()){
     * device.setDeviceId(m_ResultSet.getInt("deviceid"));
     * device.setDtype(m_ResultSet.getString("dtype"));
     * device.setDprotocol(m_ResultSet .getString("dprotocol"));
     * device.setDiemi(m_ResultSet.getString("diemi"));
     * device.setDreg_date(m_ResultSet.getString("dreg_date"));
     * device.setDsim_num(m_ResultSet .getString("dsim_num"));
     * device.setUserid(m_ResultSet.getInt("userid"));
     * device.setVehicleid(m_ResultSet.getInt("vehicleid")); }
     * }catch(SQLException e) {
     * 
     * } return device; }
     */
    public List<TrackData> getLiveLocation(User user) {

        List<TrackData> list = new ArrayList<TrackData>();

        list = getTrackDataList_Live(user, list);

        return list;

    }

    public List<TrackData> getTrackDataList_Live(User user,
            List<TrackData> list_track_data) {
        List<Long> list_deviceID = new ArrayList<Long>();
        List<Long> list_vehicleID = new ArrayList<Long>();
        String collect_vehicleIDs = "";
        //String collect_DeviceIDs="";
        if (user.getUprofile() == 3) {
            collect_vehicleIDs = "select vehicleid from vehicle";
        } else {
            collect_vehicleIDs = "select vehicleid from user_vehicle where userid in (select userid from user where ownerid = '"
                    + user.getUserId()
                    + "' or userid = '"
                    + user.getUserId()
                    + "' )";
        }
        log.info("vehicle ids--->>>" + collect_vehicleIDs);
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(collect_vehicleIDs);
            while (m_ResultSet.next()) {
                list_vehicleID.add(m_ResultSet.getLong("vehicleid"));
            }
            Iterator<Long> vehicleid_Iterator = list_vehicleID.iterator();
            String deviceIds = "";
            Long j = 0L;
            while (vehicleid_Iterator.hasNext()) {
                j = vehicleid_Iterator.next();
                if (deviceIds.equals("")) {
                    deviceIds += "select deviceid from vehicle where vehicleid='" + j + "'";
                } else {
                    deviceIds += "union " + "select deviceid from vehicle where vehicleid='" + j + "'";
                }
            }
            log.info("deviceIds---->>>" + deviceIds);
            m_ResultSet = m_Statement.executeQuery(deviceIds);
            while (m_ResultSet.next()) {
                list_deviceID.add(m_ResultSet.getLong("deviceid"));
            }

            Iterator<Long> deviceid_Iterator = list_deviceID.iterator();

            String str = "";
            Long i = 0L;
            while (deviceid_Iterator.hasNext()) {
                i = deviceid_Iterator.next();
                if (str.equals("")) {
                    str += "select vregisteration_num, Phone_No,cv,Latitude,Longitude,Speed,Acc_Status,location from livemessagedata join vehicle on livemessagedata.Phone_No=vehicle.deviceid where cv"
                            + " in( "
                            + " select max(cv) from livemessagedata where Phone_No = '" + i + "' "
                            + " ) "
                            + " and "
                            + " Phone_No = '" + i + "'";
                } else {
                    str += "union "
                            + "select vregisteration_num, Phone_No,cv,Latitude,Longitude,Speed,Acc_Status,location from livemessagedata join vehicle on livemessagedata.Phone_No=vehicle.deviceid where cv"
                            + " in( "
                            + " select max(cv) from livemessagedata where Phone_No = '" + i + "' "
                            + " ) "
                            + " and "
                            + " Phone_No = '" + i + "'";
                }
            }
            log.info(str);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            m_ResultSet = m_Statement.executeQuery(str);
            while (m_ResultSet.next()) {

                TrackData trackData = new TrackData();
                trackData.setVregisteration_num(m_ResultSet.getString("vregisteration_num"));
                trackData.setDeviceid(m_ResultSet.getString("Phone_No"));
                Date date = m_ResultSet.getTimestamp("cv");
                log.info("date -->>>  " + date);

                trackData.setDate(df.format(date));

                if (trackData.getLatitudeList().equals("")) {
                    trackData.setLatitudeList(m_ResultSet.getString("Latitude"));
                } else {
                    trackData.setLatitudeList(trackData.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                }

                if (trackData.getLongitudeList().equals("")) {
                    trackData.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    trackData.setLongitudeList(trackData.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }


                trackData.setLocation(m_ResultSet.getString("location"));

                trackData.setSpeed(Double.parseDouble(m_ResultSet.getString("Speed")));
                int x = Integer.parseInt(m_ResultSet.getString("Acc_Status"));
                if (x == 0) {
                    trackData.setAcc_status("OFF");
                } else {
                    trackData.setAcc_status("ON");
                }

                list_track_data.add(trackData);
            }
        } catch (SQLException sqe) {
            log.info(sqe);
        } catch (Exception e) {
            log.info(e);
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        //log.info("Value of  : " + trackData.getLatitudeList());
        // log.info("Value of  : " + trackData.getLongitudeList());
        return list_track_data;
    }

    public List<TrackData> getHistoryLocations(String startDate, String endDate, String vregisteration_num) {

        List<TrackData> list = new ArrayList<TrackData>();

        list = getTrackDataList_History(list, startDate, endDate, vregisteration_num);

        return list;
    }

    public List<TrackData> getTrackDataList_History(
            List<TrackData> list_track_data, String startDate, String endDate, String vregisteration_num) {

        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            String m_Query = "select Phone_No,cv,Latitude,Longitude,Speed,Acc_Status,location from livemessagedata where Phone_No in "
                    + "( "
                    + " select deviceid from vehicle where vregisteration_num = '" + vregisteration_num + "' "
                    + ") "
                    + "and "
                    + "cv between "
                    + "'" + startDate + "' "
                    + "and "
                    + "'" + endDate + "'";

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            m_ResultSet = m_Statement.executeQuery(m_Query);
            log.info(m_Query);

            while (m_ResultSet.next()) {

                TrackData trackData = new TrackData();
                trackData.setDeviceid(m_ResultSet.getString("Phone_No"));
                trackData.setDate(df.format(m_ResultSet.getTimestamp("cv")));

                if (trackData.getLatitudeList().equals("")) {
                    trackData.setLatitudeList(m_ResultSet.getString("Latitude"));
                } else {
                    trackData.setLatitudeList(trackData.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                }

                if (trackData.getLongitudeList().equals("")) {
                    trackData.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    trackData.setLongitudeList(trackData.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }

                trackData.setLocation(m_ResultSet.getString("location"));

                trackData.setSpeed(Double.parseDouble(m_ResultSet.getString("Speed")));
                int x = Integer.parseInt(m_ResultSet.getString("Acc_Status"));
                if (x == 0) {
                    trackData.setAcc_status("OFF");
                } else {
                    trackData.setAcc_status("ON");
                }

                list_track_data.add(trackData);
            }

        } catch (SQLException sqe) {
            log.info(sqe);
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list_track_data;
    }

    public void createVehicle(Vehicle vehicle) {

        String queryInsert = "insert into vehicle values('" + vehicle.getVehicleId() + "','" + vehicle.getVn() + "','" + vehicle.getRegistration_date() + "','" + vehicle.getVmake() + "','" + vehicle.getVmodel() + "','" + vehicle.getVfuel_type() + "','" + vehicle.getFleet() + "','" + vehicle.getUserid() + "','" + vehicle.getDeviceid() + "','" + vehicle.getActive() + "')";
        log.info(queryInsert + "*****************************");
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryInsert);
            log.info(i + "          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Vehicle> listVehicle(int userid, int uprofile) {
        List<Vehicle> vehicle_list = new ArrayList<Vehicle>();
        String queryList = "";
        if (uprofile == 3) {
            queryList = "select * from vehicle";
        } else {
            queryList = "select * from vehicle where vehicleid in"
                    + "(select vehicleid from user_vehicle where userid in"
                    + "(select userid from user where userid='" + userid + "' or ownerid='" + userid + "'))";
        }
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryList);

            while (m_ResultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(m_ResultSet.getLong("vehicleid"));
                vehicle.setVn(m_ResultSet.getString("vregisteration_num"));
                vehicle.setRegistration_date(m_ResultSet.getString("registration_date"));
                vehicle.setVmake(m_ResultSet.getString("vmake"));
                vehicle.setVmodel(m_ResultSet.getString("vmodel"));
                vehicle.setVfuel_type(m_ResultSet.getString("vfuel_type"));
                vehicle.setFleet(m_ResultSet.getLong("fleet"));
                vehicle.setUserid(m_ResultSet.getLong("userid"));
                vehicle.setDeviceid(m_ResultSet.getLong("deviceid"));
                vehicle.setActive(m_ResultSet.getInt("active"));
                vehicle_list.add(vehicle);
            }
        } catch (SQLException e) {
            log.info(e);
        }
        return vehicle_list;
    }

    public List<String> listUser(int userid) {
        List<String> user_list = new ArrayList<String>();
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery("select fname,lname,uemail from user where userid = '"
                    + userid
                    + "' or ownerid = '"
                    + userid
                    + "' and active = 1;");
            while (m_ResultSet.next()) {
                String fname = m_ResultSet.getString("fname");
                String lname = m_ResultSet.getString("lname");
                String email = m_ResultSet.getString("uemail");
                user_list.add("Mr. " + fname + " " + lname + "," + email);
            }
        } catch (SQLException e) {
            log.info(e);
        }
        return user_list;
    }

    public String addExistingVehicle(long vehicleid, int userid) {
        String actionReport = "";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate("insert into user_vehicle values ('" + userid + "','" + vehicleid + "');");
            if (i != 0) {
                actionReport = "group";
            } else {
                actionReport = "input";
            }
        } catch (SQLException e) {
            actionReport = "input";
        }
        return actionReport;
    }

    public List<Long> getDeviceId(long userid, int uprofile) {

        List<Long> list = new ArrayList();
        String queryLISTMSISDN = "";
        if (uprofile == 3) {
            queryLISTMSISDN = "select deviceid from device where userid in"
                    + "(select userid from user)"
                    + " and deviceid not in(select deviceid from vehicle )";
        } else {
            queryLISTMSISDN = "select deviceid from device where userid in"
                    + "(select userid from user where ownerid ='" + userid + "'  or userid ='" + userid + "')"
                    + " and deviceid not in(select deviceid from vehicle )";
        }
        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getLong("deviceid"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }

    public int getMaxVehicleId() {
        int id = 1;
        String queryMAXID = "select max(vehicleid) from vehicle";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryMAXID);
            if (m_ResultSet.next()) {

                id = m_ResultSet.getInt(1) + 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return id;
    }

    public List<Integer> getUserIdList(int userId, int uprofile) {
        List<Integer> list = new ArrayList();
        String queryLISTMSISDN = "";
        if (uprofile == 3) {
            queryLISTMSISDN = "select userid from user";
        } else {
            queryLISTMSISDN = "select userid from user where userid='" + userId + "' or ownerid='" + userId + "'";
        }
        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getInt("userid"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }

    public Vehicle getVehicle(long vehicleId) {
        String queryLISTMSISDN = "select * from vehicle where vehicleid='" + vehicleId + "' ";

        log.info(queryLISTMSISDN + "qqqqqqqqqqqqqqqqqqqqqq");

        Vehicle vehicle = new Vehicle();
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                vehicle.setVehicleId(m_ResultSet.getLong("vehicleid"));
                vehicle.setVn(m_ResultSet.getString("vregisteration_num"));
                vehicle.setRegistration_date(m_ResultSet.getString("registration_date"));
                vehicle.setVmake(m_ResultSet.getString("vmake"));
                vehicle.setVmodel(m_ResultSet.getString("vmodel"));
                vehicle.setVfuel_type(m_ResultSet.getString("vfuel_type"));
                vehicle.setFleet(m_ResultSet.getLong("fleet"));
                vehicle.setUserid(m_ResultSet.getLong("userid"));
                vehicle.setDeviceid(m_ResultSet.getLong("deviceid"));
                vehicle.setActive(m_ResultSet.getInt("active"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }

        return vehicle;

    }

    public String validateDeviceInVehicle(long deviceid, long vehicleId) {
        String actionResult = "";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery("select deviceid from vehicle where deviceid='" + deviceid
                    + "' and vehicleid='" + vehicleId + "'");
            log.info(m_ResultSet + "ResultSet");
            if (m_ResultSet.next()) {
                actionResult = "ok";
            } else {
                try {
                    m_ResultSet = m_Statement.executeQuery("select deviceid from vehicle where deviceid='" + deviceid + "'");
                    if (m_ResultSet.next()) {
                        actionResult = "deviceidExist";
                    } else {
                        actionResult = "ok";
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                } finally {
                    try {
                        if (m_ResultSet != null) {
                            m_ResultSet.close();
                        }
                        if (m_Statement != null) {
                            m_Statement.close();
                        }
                        if (m_Connection != null) {
                            m_Connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actionResult;

    }

    public String updateVehicle(Vehicle vehicle) {
        String actionResult = "";
        String queryEDITMSISDN = "update vehicle set deviceid='" + vehicle.getDeviceid() + "',vregisteration_num='"
                + vehicle.getVn() + "',vmake='" + vehicle.getVmake() + "',vmodel='" + vehicle.getVmodel() + "',vfuel_type='"
                + vehicle.getVfuel_type() + "' where vehicleid='" + vehicle.getVehicleId() + "' ";

        log.info(queryEDITMSISDN + "!!!!!!!!!!!!!!!!!!!!!!");
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryEDITMSISDN);
            if (i != 0) {
                actionResult = "updated";
            } else {
                actionResult = "notUpdated";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return actionResult;
    }

    public void deleteVehicle(long vehicleId) {

        String queryREMOVEMSISDN = "delete from vehicle where vehicleid='" + vehicleId + "'";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryREMOVEMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<Integer> getVehicleIdList(int userId, int uprofile) {
        List<Integer> list = new ArrayList();
        String queryLISTMSISDN = "";
        if (uprofile == 3) {
            queryLISTMSISDN = "select vehicleid from vehicle";
        } else {
            queryLISTMSISDN = "select vehicleid from vehicle where vehicleid in"
                    + "(select vehicleid from user_vehicle where userid in"
                    + "(select userid from user where ownerid ='" + userId + "' or userid ='" + userId + "'))";
        }
        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getInt("vehicleid"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }

    public List<String> getVehicleList(User user) {
        List<String> list = new ArrayList<String>();
        String str = "";
        if (user.getUprofile() == 3) {
            str = "select vregisteration_num from vehicle ";
        } else {
            str = "select vregisteration_num from vehicle where vehicleid in"
                    + "(select vehicleid from user_vehicle where userid in"
                    + "(select userid from user where ownerid ='" + user.getUserId() + "' or userid ='" + user.getUserId() + "'))";
        }
        log.info("vregistration_num " + str);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(str);
            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getString(1));
            }
        } catch (SQLException e) {
            log.info(e);
        }
        return list;
    }

    public List<Report> createTrip(User user, String startDate,
            String endDate, String vregistration_num) { // method
        // here.
        List<Report> list = new ArrayList<Report>();

        List<Integer> acc_status_List = new ArrayList<Integer>();
        List<Integer> message_id_List_of_Trip_Report = new ArrayList<Integer>();
        List<Integer> message_id_List = new ArrayList<Integer>();

        String trip_Query = "select Message_ID,Acc_Status from "
                + "livemessagedata where cv between '" + startDate
                + "' and '" + endDate + "' "
                + "and Phone_No in( select deviceid from vehicle where"
                + " vregisteration_num = '" + vregistration_num + "')";
        log.info(trip_Query);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(trip_Query);
            while (m_ResultSet.next()) {
                message_id_List.add(m_ResultSet.getInt("Message_ID"));
                acc_status_List.add(Integer.parseInt(m_ResultSet.getString("Acc_Status")));
            }
            for (int m : message_id_List) {
                // log.info("#####" + m);
            }
            for (int m : acc_status_List) {
                //  log.info("#####" + m);
            }
            int flag = 0;
            for (int k = 0; k < acc_status_List.size() - 1; k++) {
                if (acc_status_List.get(0) == 0) {
                    flag = 1;
                }
                if (acc_status_List.get(0) == 1) {
                    TripReportAction.setStart_location_flag(1);
                }

                if ((acc_status_List.get(k) == 0 && acc_status_List.get(k + 1) == 1)) {
                    //if(flag == 0)
                    //flag = 1; // if flag = 1 , 
                    message_id_List_of_Trip_Report.add(message_id_List.get(k + 1));
                }
                if ((acc_status_List.get(k) == 1 && acc_status_List.get(k + 1) == 0)) {
                    message_id_List_of_Trip_Report.add(message_id_List.get(k + 1));
                }
            }
            for (int m : message_id_List_of_Trip_Report) {
                System.out.print("**** :" + m);
            }

            String SQL_trip = "";
            Iterator<Integer> trip_row_num = message_id_List_of_Trip_Report.iterator();
            while (trip_row_num.hasNext()) {
                if (SQL_trip.equals("")) {
                    SQL_trip += "select cv,Latitude,Longitude from livemessagedata where Message_ID='"
                            + trip_row_num.next() + "'";
                } else {
                    SQL_trip += "or Message_ID='" + trip_row_num.next() + "'";
                }
            }
            log.info(SQL_trip);
            m_ResultSet = m_Statement.executeQuery(SQL_trip);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            int flag_flag = 0;

            while (m_ResultSet.next()) {
                java.util.Date start_d = null;
                java.util.Date end_d = null;
                Report report = new Report();
                log.info("here in report..");
                if (report.getLatitudeList() != null) {
                    log.info("latitute is no null..");

                    if (report.getLatitudeList().equals("")) {
                        report.setLatitudeList(m_ResultSet.getString("Latitude"));
                    } else {
                        report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                    }
                } else {
                    log.info("latitude is null...");
                }
                if (report.getLongitudeList().equals("")) {
                    report.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }
                log.info("starting flag");
                if (flag_flag == 0) {
                    flag_flag = 1;
                    if (flag == 1) {
                        start_d = m_ResultSet.getTimestamp("cv");
                        report.setStartDate(df.format(start_d));

                        if (m_ResultSet.next()) {
                            end_d = m_ResultSet.getTimestamp("cv");
                            report.setEndDate(df.format(end_d));
                            if (report.getLatitudeList().equals("")) {
                                report.setLatitudeList(m_ResultSet.getString("Latitude"));
                            } else {
                                report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                            }

                            if (report.getLongitudeList().equals("")) {
                                report.setLongitudeList(m_ResultSet.getString("Longitude"));
                            } else {
                                report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                            }
                        }
                    } else {
                        end_d = m_ResultSet.getTimestamp("cv");
                        report.setEndDate(df.format(end_d));
                    }
                    if (start_d != null) {
                        report.setDuration(duration(start_d, end_d));
                    } else {
                        report.setDuration("Can not find");
                    }
                } else {
                    start_d = m_ResultSet.getTimestamp("cv");
                    report.setStartDate(df.format(start_d));

                    if (m_ResultSet.next()) {
                        end_d = m_ResultSet.getTimestamp("cv");
                        report.setEndDate(df.format(end_d));
                        if (report.getLatitudeList().equals("")) {
                            report.setLatitudeList(m_ResultSet.getString("Latitude"));
                        } else {
                            report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                        }

                        if (report.getLongitudeList().equals("")) {
                            report.setLongitudeList(m_ResultSet.getString("Longitude"));
                        } else {
                            report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                        }
                    }
                    if (end_d != null) {
                        report.setDuration(duration(start_d, end_d));
                    } else {
                        report.setDuration("Can not find");

                    }
                    log.info("before report");
                }
                list.add(report);
            }



        } catch (SQLException e) {
            log.info(e);
        } catch (NoSuchElementException ne) {
            log.info("############### EXCEPTION.. : " + ne);
        }

        return list;
    } // createTrip() Ends here.

    public List<Report> createStoppage(User user, String startDate, String endDate,
            String vregistration_num) {
        List<Report> list = new ArrayList<Report>();

        String history_Query = "select cv,Latitude,Longitude,Speed,Mileage_Hexa,Acc_Status,Availability_GPS from "
                + "livemessagedata where cv between '" + startDate
                + "' and '" + endDate + "' "
                + "and Phone_No in ( select deviceid from vehicle where"
                + " vregisteration_num = '" + vregistration_num + "') and speed=0.00 ";

        log.info("***************" + history_Query);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(history_Query);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            while (m_ResultSet.next()) {

                Report report = new Report();

                report.setStartDate(df.format(m_ResultSet.getTimestamp("cv")));
                if (report.getLatitudeList().equals("")) {
                    report.setLatitudeList(m_ResultSet.getString("Latitude"));
                } else {
                    report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                }

                if (report.getLongitudeList().equals("")) {
                    report.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }

                report.setSpeed(m_ResultSet.getString("Speed"));
                report.setDistance(m_ResultSet.getString("Mileage_Hexa"));
                int acc_status = Integer.parseInt(m_ResultSet.getString("Acc_Status"));
                if (acc_status == 1) {
                    report.setIgnition("ON");
                } else {
                    report.setIgnition("OFF");
                }
                String gps = m_ResultSet.getString("Availability_GPS");
                if (gps.equals("A")) {
                    report.setGPS("Available");
                } else {
                    report.setGPS("Not Available");
                }
                list.add(report);
            }

        } catch (SQLException e) {
            log.info(e + "**********************");
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return list;
    }

    //RITESH
    public String duration(Date date1, Date date2) {
        String total_duration = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyy dd:mm:ss");
            String date_S1 = sdf.format(date1);
            String date_S2 = sdf.format(date2);
            Date d1 = sdf.parse(date_S1);
            Date d2 = sdf.parse(date_S2);
            long diff = d2.getTime() - d1.getTime();
            long sec = diff / 1000;
            long year = sec / (60 * 60 * 24 * 365);
            long month = (sec - year * 60 * 60 * 24 * 365) / (60 * 60 * 24 * 30);
            long day = (sec - year * 60 * 60 * 24 * 365 - month * 60 * 60 * 24 * 30)
                    / (60 * 60 * 24);
            long hour = (sec - year * 60 * 60 * 24 * 365 - month * 60 * 60 * 24
                    * 30 - day * 60 * 60 * 24)
                    / (60 * 60);
            long min = (sec - year * 60 * 60 * 24 * 365 - month * 60 * 60 * 24 * 30
                    - day * 60 * 60 * 24 - hour * 60 * 60) / (60);
            long seconds = (sec - year * 60 * 60 * 24 * 365 - month * 60 * 60 * 24
                    * 30 - day * 60 * 60 * 24 - hour * 60 * 60 - min * 60);

            total_duration = year + " YEAR " + month + " MONTHS " + day + " DAYS "
                    + hour + " HOUR " + min + " MIN " + seconds + " SEC ";
        } catch (Exception e) {
            log.info(e);
        }
        return total_duration;
    }

    public List<Report> createIdealTime(User user, String startDate,
            String endDate, String vregistration_num) {

        Map<Integer, java.util.Date> date_List = new HashMap<Integer, java.util.Date>();

        List<Report> list = new ArrayList<Report>();

        List<Double> speed_List = new ArrayList<Double>();
        List<Integer> acc_status_List = new ArrayList<Integer>();
        List<Integer> message_id_List_of_Ideal_Report = new ArrayList<Integer>();
        List<Integer> message_id_List = new ArrayList<Integer>();

        String trip_Query = "select Message_ID,cv,Speed,Acc_Status from "
                + "livemessagedata where cv between '" + startDate
                + "' and '" + endDate + "' "
                + "and Phone_No in( select deviceid from vehicle where"
                + " vregisteration_num = '" + vregistration_num + "')";
        log.info("Idel time report--->" + trip_Query);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(trip_Query);
            while (m_ResultSet.next()) {
                int id = m_ResultSet.getInt("Message_ID");
                message_id_List.add(id);
                date_List.put(id, m_ResultSet.getTimestamp("cv"));

                // log.info(date_List.get(id));
                speed_List.add(Double.parseDouble(m_ResultSet.getString("Speed")));
                acc_status_List.add(Integer.parseInt(m_ResultSet.getString("Acc_Status")));
            }
            for (int m : message_id_List) {
                // log.info("#####" + m);
            }
            for (double m : speed_List) {
                // log.info("speed -->" + m);
            }
            for (int m : acc_status_List) {
                //  log.info("#####" + m);
            }
            int flag = 0;
            int flagDummy = 0;//use to get start and end point location
            int start_Message_ID = 0;
            int end_Message_ID = 0;

            for (int k = 0; k < speed_List.size() - 1; k++) {
                log.info("value of itration --->" + k);

                if (speed_List.get(k) > 0.00 && (speed_List.get(k + 1) == 0.00 && acc_status_List.get(k + 1) == 1)) {
                    // log.info("hello in if");   
                    log.info("value of k in if condition -->" + k);
                    log.info("speed-->" + speed_List.get(k));
                    // log.info("acc_status-->" + acc_status_List.get(k + 1));
                    /**
                     * Make it start point.
                     */
                    start_Message_ID = message_id_List.get(k + 1);
                    log.info("start msg id  -->" + start_Message_ID);
                    flagDummy = 1;
                    flag = 1; // Represent start point.
                    IdealTimeReportAction.setStart_location_flag(1);
                }// if close here..
                else {
                    if (flagDummy == 1) {
                        if (speed_List.get(k) == 0 && acc_status_List.get(k) == 1) {
                            continue;
                            //Here , we got continous ideal condition. So , make it continue for find out end point of ideal session.
                        } else {
                            end_Message_ID = message_id_List.get(k);
                            log.info("value of k in else condition -->" + k);
                            log.info("End msg Id -->" + end_Message_ID);
                            flagDummy = 0;
                            // This is the end point of ideal session.
                        }
                    } else {
                        continue;
                    }
                }
                /*
                Now check for Ideal duration sould be greater than 2 minutes.
                 */
                try {
                    //log.info(start_Message_ID + " End /" + end_Message_ID);
                    long t1 = 0L;
                    long t2 = 0L;

                    if (start_Message_ID != 0 && end_Message_ID != 0) {
                        log.info(start_Message_ID + " End /" + end_Message_ID);
                        log.info("here if start and end msgid not o");
                        t1 = date_List.get(end_Message_ID).getTime();
                        log.info("value of t1-->" + t1);
                        //.getTime();
                        // log.info("there");
                    } else {
                        t1 = 0L;
                    }
                    if (start_Message_ID != 0 && end_Message_ID != 0) {
                        t2 = date_List.get(start_Message_ID).getTime();
                        log.info("value of t2-->" + t2);
                    } else {
                        t2 = 0L;
                    }
                    if (start_Message_ID != 0 && end_Message_ID != 0) {
                        log.info("value of time difference" + (t1 - t2));
                        log.info("value of 2 min" + (2 * 60 * 1000));
                        if ((t1 - t2) > (2 * 60 * 1000)) {
                            message_id_List_of_Ideal_Report.add(start_Message_ID);
                            message_id_List_of_Ideal_Report.add(end_Message_ID);
                            start_Message_ID = 0;
                            end_Message_ID = 0;
                            log.info("in t2 - t2---->>>");
                            for (int m : message_id_List_of_Ideal_Report) {
                                log.info("message_id_List_of_Ideal_Report-->" + m);
                            }

                        } else {
                            log.info("clean the value of start and end msg_id");
                            start_Message_ID = 0;
                            end_Message_ID = 0;
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    log.info(e + " : This is the reason.");
                    break;
                }
                // stop 2 mincalculation logic
            }

            String SQL_trip = "";
            Iterator<Integer> trip_row_num = message_id_List_of_Ideal_Report.iterator();
            while (trip_row_num.hasNext()) {
                if (SQL_trip.equals("")) {
                    SQL_trip += "select cv,Latitude,Longitude from livemessagedata where Message_ID='"
                            + trip_row_num.next() + "'";
                } else {
                    SQL_trip += "or Message_ID='" + trip_row_num.next() + "'";
                }
            }
            log.info("SQL-Trip----->" + SQL_trip);
            m_ResultSet = m_Statement.executeQuery(SQL_trip);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");

            while (m_ResultSet.next()) {
                java.util.Date start_d = null;
                java.util.Date end_d = null;
                Report report = new Report();
                if (report.getLatitudeList().equals("")) {
                    report.setLatitudeList(m_ResultSet.getString("Latitude"));
                } else {
                    report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                }

                if (report.getLongitudeList().equals("")) {
                    report.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }
                start_d = m_ResultSet.getTimestamp("cv");
                report.setStartDate(df.format(start_d));

                if (m_ResultSet.next()) {
                    end_d = m_ResultSet.getTimestamp("cv");
                    report.setEndDate(df.format(end_d));
                }
                if (start_d != null) {
                    report.setDuration(duration(start_d, end_d));
                } else {
                    report.setDuration("Can not find");
                }
                list.add(report);
            }
        } catch (SQLException e) {
            log.info(e);
        } catch (NoSuchElementException ne) {
            log.info("############### EXCEPTION.. : " + ne);
        }
        return list;
    }

    public List<Report> createHistory(User user, String startDate,
            String endDate, String vregistration_num) {


        List<Report> list = new ArrayList<Report>();

        String history_Query = "select cv,Latitude,Longitude,Speed,Mileage_Hexa,Acc_Status,Availability_GPS from "
                + "livemessagedata where cv between '" + startDate
                + "' and '" + endDate + "' "
                + "and Phone_No in "
                + "(select deviceid from vehicle where vregisteration_num = '" + vregistration_num + "')";

        log.info("***************" + history_Query);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(history_Query);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            while (m_ResultSet.next()) {

                Report report = new Report();

                report.setStartDate(df.format(m_ResultSet.getTimestamp("cv")));
                if (report.getLatitudeList().equals("")) {
                    report.setLatitudeList(m_ResultSet.getString("Latitude"));
                } else {
                    report.setLatitudeList(report.getLatitudeList() + "," + m_ResultSet.getString("Latitude"));
                }

                if (report.getLongitudeList().equals("")) {
                    report.setLongitudeList(m_ResultSet.getString("Longitude"));
                } else {
                    report.setLongitudeList(report.getLongitudeList() + "," + m_ResultSet.getString("Longitude"));
                }
                report.setSpeed(m_ResultSet.getString("Speed"));
                report.setDistance(m_ResultSet.getString("Mileage_Hexa"));
                int acc_status = Integer.parseInt(m_ResultSet.getString("Acc_Status"));
                if (acc_status == 1) {
                    report.setIgnition("ON");
                } else {
                    report.setIgnition("OFF");
                }
                String gps = m_ResultSet.getString("Availability_GPS");
                if (gps.equals("A")) {
                    report.setGPS("Available");
                } else {
                    report.setGPS("Not Available");
                }
                list.add(report);
            }

        } catch (SQLException e) {
            log.info(e + "**********************");
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return list;
    }

    public List<Report> createOverSpeed(User user, String startDate, String endDate, String vregistration_num, int overSpeed) {

        List<Report> list = new ArrayList<Report>();
        String overSpeedString = "select cv,location,Speed,Mileage_Hexa,Acc_Status,Availability_GPS from livemessagedata where cv between '" + startDate + "' and '2012/04/28 01:52:17' and Phone_No in (select deviceid from vehicle where vregisteration_num = '1')";
        log.info("overSpeedString : "+overSpeedString);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(overSpeedString);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            while (m_ResultSet.next()) {
                //log.info("******" + overSpeed + "speed  : " + Float.parseFloat(m_ResultSet.getString("speed")));

                //checking condition in which speed id greater than overspeed
                if (Float.parseFloat(m_ResultSet.getString("speed")) >= overSpeed) {
                    Report report = new Report();
                    report.setStartDate(df.format(m_ResultSet.getTimestamp("cv")));
                    report.setLocation(m_ResultSet.getString("location"));
                    report.setSpeed(m_ResultSet.getString("speed"));

                    int acc_status = Integer.parseInt(m_ResultSet.getString("Acc_Status"));
                    float speed = Float.parseFloat(m_ResultSet.getString("speed"));
                    //For setting values of status field
                    if (acc_status == 1 && speed == 0) {
                        report.setIgnition("Dormant");//vehile is start but not running(ignition on but speed == 0 )
                        //log.info("dormant");
                    } else {
                        if (acc_status == 1 && speed > 0) {
                            report.setIgnition("In Motion");//vehile in running (speed > o)
                            // log.info("in motion");
                        } else {
                            if (acc_status == 0) {
                                report.setIgnition("Stop");//ignition off
                                // log.info("stop");
                            }
                        }
                    }

//                    if (acc_status == 1) {
//                        report.setIgnition("ON");
//                    } else {
//                        report.setIgnition("OFF");
//                    }
                    String gps = m_ResultSet.getString("Availability_GPS");
                    if (gps.equals("A")) {
                        report.setGPS("Available");
                    } else {
                        report.setGPS("Not Available");
                    }


                    list.add(report);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return list;
    }

    public List<User> listViewUser(int uprofile, int userId) {

        List<User> list = null;

        String queryLISTMSISDN = "";

        if (uprofile == 3) {
            queryLISTMSISDN = "select userid,fname,lname,baddress,haddress,utype,umobile,uemail,active from user ";
        } else {
            queryLISTMSISDN = "select userid,fname,lname,baddress,haddress,utype,umobile,uemail,active from user where"
                    + " userid='" + userId + "' or uprofile='" + userId + "'";
        }
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                if (list == null) {
                    list = new ArrayList<User>();
                }
                User user = new User();
                user.setUserId(m_ResultSet.getInt(1));
                user.setFname(m_ResultSet.getString(2));
                user.setLname(m_ResultSet.getString(3));
                user.setBaddress(m_ResultSet.getString(4));
                user.setHaddress(m_ResultSet.getString(5));
                user.setUtype(m_ResultSet.getString(6));
                user.setUmobile(m_ResultSet.getString(7));
                user.setUemail(m_ResultSet.getString(8));
                user.setActive(m_ResultSet.getInt(9));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
        return list;

    }

    public int getMaxUserId() {

        int id = 1;
        String queryMAXID = "select max(userid) from user";

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryMAXID);
            if (m_ResultSet.next()) {

                id = m_ResultSet.getInt(1) + 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return id;
    }
    // use to get users email list

    public List<String> getUserId(int userId, int uprofile) {

        String queryLISTMSISDN = "";
        List<String> userIdList = new ArrayList();
        if (uprofile == 3) {
            queryLISTMSISDN = "select uemail from user;";
        } else {
            queryLISTMSISDN = "SELECT uemail from user where ownerid='" + userId + "' or userid='" + userId + "' ";
        }
        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                userIdList.add(m_ResultSet.getString("uemail"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userIdList;
    }

    public User getUser(String uemail, int userId_Owner) {
        String //queryLISTMSISDN = "select userid,fname,lname,baddress,haddress,utype,umobile,uemail,password,uprofile,active from trackman.user where userid='"+ userId +"' and ownerid='"+ userId_Owner +"' ";
                queryLISTMSISDN = "select * from user where uemail='" + uemail + "' ";

        log.info(queryLISTMSISDN + "qqqqqqqqqqqqqqqqqqqqqq");

        User user = new User();
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                user.setUserId(m_ResultSet.getInt("userid"));
                user.setFname(m_ResultSet.getString("fname"));
                user.setLname(m_ResultSet.getString("lname"));
                user.setBaddress(m_ResultSet.getString("baddress"));
                user.setHaddress(m_ResultSet.getString("haddress"));
                user.setUtype(m_ResultSet.getString("utype"));
                user.setUmobile(m_ResultSet.getString("umobile"));
                user.setUemail(m_ResultSet.getString("uemail"));
                user.setPassword(m_ResultSet.getString("password"));
                user.setUprofile(m_ResultSet.getInt("uprofile"));
                user.setOwnerid(m_ResultSet.getInt("ownerid"));
                user.setActive(m_ResultSet.getInt("active"));
                user.setAdmin_id(m_ResultSet.getInt("admin_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }

        return user;
    }

    public void updateUser(User user, int userId_Owner) {

        String queryEDITMSISDN = "update user set fname='" + user.getFname() + "', lname='" + user.getLname() + "', baddress='" + user.getBaddress() + "',"
                + "haddress='" + user.getHaddress() + "', utype='" + user.getUtype() + "', umobile='" + user.getUmobile() + "',uemail='" + user.getUemail() + "',"
                + "password='" + user.getPassword() + "' , uprofile='" + user.getUprofile() + "',active='" + user.getActive() + "' where userid='" + user.getUserId() + "' ";

        log.info(queryEDITMSISDN + "!!!!!!!!!!!!!!!!!!!!!!");

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryEDITMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String updatePassword(int userId, String password, String newPassword) {

        String querySELECTMSISDN = "select password from user where password='" + password + "' and userid='" + userId + "'";
        log.info(querySELECTMSISDN + "<<-----");
        String queryUPDATE = "UPDATE user SET password='" + newPassword + "' WHERE userid='" + userId + "'";
        log.info("update  password-->" + queryUPDATE);
        String result = "";
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(querySELECTMSISDN);
            if (m_ResultSet.next()) {
                int i = m_Statement.executeUpdate(queryUPDATE);
                result = "updated";
            } else {
                result = "psdInput";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public List<Long> getDeviceIdForDevice(int userid, int uprofile) {
        List<Long> list = new ArrayList<Long>();
        String insertQuery = "";
        if (uprofile == 3) {
            insertQuery = "select deviceid from device where userid in (select userid from user )";
        } else {
            insertQuery = "select deviceid from device where userid in"
                    + "("
                    + " select userid from user where ownerid = '" + userid + "' or userid = '" + userid + "'"
                    + ")"
                    + "";
            log.info("list of devices" + insertQuery);
        }
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(insertQuery);
            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getLong("deviceid"));
            }
        } catch (SQLException e) {
            log.info("EditMgtDeviceAction  : " + e);
        }
        return list;
    }

    public List<Vehicle> searchVehicle(String vn, int userId) {
        List<Vehicle> vehicle_list = new ArrayList<Vehicle>();
        String str = "select * from vehicle where vregisteration_num='" + vn + "'";
        log.info("Search vehicle --->>>" + str);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(str);

            while (m_ResultSet.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(m_ResultSet.getLong("vehicleid"));
                vehicle.setVn(m_ResultSet.getString("vregisteration_num"));
                vehicle.setRegistration_date(m_ResultSet.getString("registration_date"));
                vehicle.setVmake(m_ResultSet.getString("vmake"));
                vehicle.setVmodel(m_ResultSet.getString("vmodel"));
                vehicle.setVfuel_type(m_ResultSet.getString("vfuel_type"));
                vehicle.setFleet(m_ResultSet.getLong("fleet"));
                vehicle.setUserid(m_ResultSet.getLong("userid"));
                vehicle.setDeviceid(m_ResultSet.getLong("deviceid"));
                vehicle.setActive(m_ResultSet.getInt("active"));
                vehicle_list.add(vehicle);
            }
        } catch (SQLException e) {
            log.info(e);
        }
        return vehicle_list;
    }

    public List<Integer> getDeviceListPayment(int accNo) {

        List<Integer> list = new ArrayList();

        String queryLISTMSISDN = "select deviceid from device where userid='" + accNo + "'";


        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getInt("deviceid"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;

    }

    public void savePayment(Payment payment) {
        // throw new UnsupportedOperationException("Not yet implementedinsert into payment values(1,1,350,'cash',09/02/2011,350,12121212,'pnb','noida','payoffer',0,0,0,'',0);");
        String queryINSERTMSISDN = "insert into payment values('" + payment.getAccNo() + "','" + payment.getDeviceId()
                + "','" + payment.getNetAmount() + "','" + payment.getPaymentMode() + "','" + payment.getPaymentDate()
                + "','" + payment.getReceivedAmt() + "','" + payment.getChqDdNo() + "','" + payment.getBankName()
                + "','" + payment.getBranchName() + "','" + payment.getOfferName() + "','" + payment.getOfferAmt() + "','" + payment.getAdjusment()
                + "','" + payment.getDiscount() + "','" + payment.getCouponNo() + "','" + payment.getCouponDiscount() + "')";

        log.info(queryINSERTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryINSERTMSISDN);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void saveOffer(Offer ofr) {
        // throw new UnsupportedOperationException("Not yet implementedinsert into payment values(1,1,350,'cash',09/02/2011,350,12121212,'pnb','noida','payoffer',0,0,0,'',0);");
        String queryINSERTMSISDN = "insert into defining_offer(offer_name,offer_amount,billing_cycle,assignto) values('" + ofr.getOfferName() + "'," + ofr.getOfferAmount() + "," + ofr.getBillingCycle() + "," + ofr.getAssignTo() + ")";


        log.info(queryINSERTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            // System.err.println("Problem");
            int i = m_Statement.executeUpdate(queryINSERTMSISDN);
            System.err.println("Offer saved Successfully.." + i);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<Long> getUserIdList() {
        List<Long> list = new ArrayList();

        String queryLISTMSISDN = "select userid from user where uprofile=" + '2';
        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                list.add(m_ResultSet.getLong("userid"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }

    public List<Offer> listOffer(long userid, long uprofile) {

        List<Offer> list = null;
        String listOffer;
        if (uprofile == 3) {
            listOffer = "select offer_name,offer_amount,billing_cycle from trackman.defining_offer";
        } else {
            listOffer = "select offer_name,offer_amount,billing_cycle from trackman.defining_offer where assignto='" + userid + "'";
        }
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(listOffer);

            while (m_ResultSet.next()) {
                if (list == null) {
                    list = new ArrayList<Offer>();
                }
                Offer offer = new Offer();
                offer.setOfferName(m_ResultSet.getString(1));
                offer.setOfferAmount(m_ResultSet.getFloat(2));
                offer.setBillingCycle(m_ResultSet.getString(3));


                list.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
        return list;

    }

    public void saveCoupons(Coupon coupon, String str) {
        String queryINSERTMSISDN = null;
        if (str.equals("definingpreassigned_coupons")) {
            queryINSERTMSISDN = "insert into " + str + " values('" + coupon.getCouponId() + "','"
                    + coupon.getCouponName() + "','" + coupon.getNumberOfCoupons() + "','" + coupon.getDiscount() + "','"
                    + 3 + "','" + coupon.getAssign() + "','" + coupon.getValidTill() + "','" + 0 + "','" + 0 + "'" + ")";

        } else {
            queryINSERTMSISDN = "insert into " + str + " values('" + coupon.getCouponId() + "','"
                    + coupon.getCouponName() + "','" + coupon.getNumberOfCoupons() + "','" + coupon.getDiscount() + "','"
                    + 1 + "','" + coupon.getAssign() + "','" + coupon.getValidTill() + "','" + 0 + "','" + 0 + "'" + ")";
        }


        log.info(queryINSERTMSISDN + "--------------------" + coupon.getAssign());
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            int i = m_Statement.executeUpdate(queryINSERTMSISDN);

            System.err.println("Coupons saved Successfully.." + i);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void preAssignedCoupon() {
        String querySELECTMSISDN = "select coupon_id from definingpreassigned_coupons";
        String querySELECTSISDN1 = "select deviceid,dreg_date from device order by dreg_date";



        log.info(querySELECTMSISDN);
        log.info(querySELECTSISDN1);
        try {

            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(querySELECTMSISDN);

            System.err.println("Query1 execution Successfully....");
            System.err.println("Query2 execution Successfully....");
            List<String> list1 = new ArrayList<String>();
            List<Long> list2 = new ArrayList<Long>();
            List<String> list3 = new ArrayList<String>();

            while (m_ResultSet.next()) {
                list1.add(m_ResultSet.getString(1));

            }

            Statement m_Statement1 = m_Connection.createStatement();
            ResultSet m_ResultSet1 = m_Statement.executeQuery(querySELECTSISDN1);
            log.info("List1 size " + list1.size());
            while (m_ResultSet1.next()) {
                list2.add(m_ResultSet1.getLong(1));
                list3.add(m_ResultSet1.getString(2));

            }
            log.info("List2 size " + list2.size());
            log.info("List3 size " + list3.size());
            int i = 0;
            int j = list1.size();

            String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat sdf =
                    new SimpleDateFormat(DATE_FORMAT);
            Calendar c1 = Calendar.getInstance(); // today
            //    log.info("Today is " + sdf.format(c1.getTime()));

            //  String queryINSERTMSISDN = "insert into assign_coupon values('" + list1.get(i) + "','" + list2.get(i) + "','" + list3.size() + "'," + sdf.format(c1.getTime()) + ")";

            while (j > 0) {
                String queryINSERTMSISDN = "insert into assign_coupon_device values('" + list1.get(i) + "','" + list2.get(i) + "','" + list3.get(i) + "','" + sdf.format(c1.getTime()) + "')";
                log.info(queryINSERTMSISDN);
                m_Statement.executeUpdate(queryINSERTMSISDN);
                i++;
                j--;
            }

            String queryINSERTMSISDN1 = "update definingpreassigned_coupons set STATUS_COUPON=" + 1;

//int l=m_Statement.executeUpdate(queryINSERTMSISDN1);
            log.info("Number of rows updated " + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void createPlan(Plan plan) {

        try {

            String s = "insert into defining_plan (PLAN_NAME,RENTAL,BILLING_CYCLE,FACILITIES,ASSIGNTO) values('" + plan.getPlanname() + "','" + plan.getRental() + "','" + plan.getBillingcycle() + "','" + plan.getFacilities() + "','" + plan.getAssignto() + "')";
            //log.info(queryINSERTMSISDN);


            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_Statement.executeUpdate(s);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    }

    public List<Plan> listPlan() {
        List<Plan> list = new ArrayList<Plan>();
        String queryLISTDEVICE = "select * from defining_plan";
        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                Plan plan = new Plan();
                plan.setPlanname(m_ResultSet.getString(2));
                plan.setRental(m_ResultSet.getFloat(3));
                plan.setBillingcycle(m_ResultSet.getString(4));
                plan.setFacilities(m_ResultSet.getString(7));
                plan.setAssignto(m_ResultSet.getInt(8));
                list.add(plan);

            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public List<Plan> listPlans(int userID, int uprofile) {
        List<Plan> list = new ArrayList<Plan>();
        String queryLISTDEVICE;
        if (uprofile == 3) {
            queryLISTDEVICE = "select * from defining_plan";
        } else {
            queryLISTDEVICE = "select * from defining_plan where assignto =" + userID;
        }
        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                Plan plan = new Plan();
                plan.setPlanname(m_ResultSet.getString(2));
                plan.setRental(m_ResultSet.getFloat(3));
                plan.setBillingcycle(m_ResultSet.getString(4));
                plan.setFacilities(m_ResultSet.getString(7));
                plan.setAssignto(m_ResultSet.getInt(8));
                list.add(plan);

            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public void createTax(Tax tax) {

        try {
            String st = "truncate table define_service_text";

            String s = "insert into define_service_text(SERVICE_TEX,EDU_CESS,S_H_EDU_CESS,OTHER_TEX) values('" + tax.getServiceTax() + "','" + tax.getEducationCess() + "','" + tax.getSheCess() + "','" + tax.getOtherTax() + "')";



            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_Statement.executeQuery(st);
            int i = m_Statement.executeUpdate(s);
            log.info("@@@@@@@@@@@@@@@@@@@" + i);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    }

    public List<Tax> updateTax() {
        List<Tax> list = new ArrayList<Tax>();
        String queryLISTDEVICE;

        queryLISTDEVICE = "select * from define_service_text";

        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                Tax tax = new Tax();
                tax.setServiceTax(m_ResultSet.getFloat(2));
                tax.setEducationCess(m_ResultSet.getFloat(3));
                tax.setSheCess(m_ResultSet.getFloat(4));
                tax.setOtherTax(m_ResultSet.getFloat(5));
                list.add(tax);

            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public void editTax(Tax tax) {
        String queryEDITMSISDN = "update define_service_text set SERVICE_TEX='"
                + tax.getServiceTax() + "', EDU_CESS='" + tax.getEducationCess()
                + "', S_H_EDU_CESS='" + tax.getSheCess() + "', OTHER_TEX='"
                + tax.getOtherTax() + "' where SERVICEID=1 ";

        log.info(queryEDITMSISDN + "-------------");
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            int i = m_Statement.executeUpdate(queryEDITMSISDN);
            log.info(i + "-------------");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Long> getDeviceId(int userID, int uprofile) {
        List<Long> list = new ArrayList<Long>();
        String queryLISTDEVICE = null;
        if (uprofile == 3) {
            queryLISTDEVICE = "select DEVICEID from device";
        } else {
            queryLISTDEVICE = "select DEVICEID from device where userid in(select userid from user where userid='" + userID + "' or ownerid='" + userID + "')";

        }
        log.info("Device Id List : " + queryLISTDEVICE);

        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {


                list.add(m_ResultSet.getLong("DEVICEID"));
            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public List<String> getPlanName(int userID, int uprofile) {

        List<String> list = new ArrayList<String>();
        String queryLISTDEVICE = null;;
        if (uprofile == 3) {

            queryLISTDEVICE = "select PLAN_NAME from defining_plan";
        } else {
            queryLISTDEVICE = "select PLAN_NAME from defining_plan where ASSIGNTO='" + userID + "'";

        }

        // queryLISTDEVICE="select PLAN_NAME from defining_plan where ASSIGNTO =+'"+11+"'+";


        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);

            log.info(queryLISTDEVICE + "8888888888888*************");
            while (m_ResultSet.next()) {

                list.add(m_ResultSet.getString("PLAN_NAME"));
            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public List<String> getSmsPlan() {
        List<String> list = new ArrayList<String>();
        String queryLISTDEVICE;

        queryLISTDEVICE = "select SMS_PLAN from sms_plan";

        log.info(queryLISTDEVICE);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {

                list.add(m_ResultSet.getString("SMS_PLAN"));

            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public void assignPlan(long deviceId, String plan, String smsPlan) {
        try {

            String s = "insert into assign_plan(DEVICEID,PLAN_NAME,SMS_PLAN)values('" + deviceId + "','" + plan + "','" + smsPlan + "')";
            log.info(s);
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            int i = m_Statement.executeUpdate(s);
            log.info("Assign Plan" + i);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    }

    public List<Integer> getUserIdPlan(int userId) {

        String queryLISTMSISDN = "";
        List<Integer> userIdList = new ArrayList();

        queryLISTMSISDN = "select userid from user where uprofile=2";

        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                userIdList.add(m_ResultSet.getInt(1));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userIdList;
    }
//work for bill generation

    public Plan getPlanInfo(long deviceid) {
        Plan plan = new Plan();
        String queryLISTMSISDN = "select * from defining_plan where plan_name in"
                + "(select plan_name from assign_plan where deviceid='" + deviceid + "') ";
        log.info("select plan details--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                plan.setPlanname(m_ResultSet.getString("PLAN_NAME"));
                plan.setRental(m_ResultSet.getLong("RENTAL"));
                plan.setBillingcycle(m_ResultSet.getString("BILLING_CYCLE"));
                plan.setDiscount(m_ResultSet.getLong("DISCOUNT"));
                plan.setTatalrental(m_ResultSet.getLong("TOTAL_RENTAL"));
                plan.setFacilities(m_ResultSet.getString("FACILITIES"));
                plan.setAssignto(m_ResultSet.getInt("ASSIGNTO"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return plan;
    }

    public Bill getPreviousBillInfo(long deviceid) {
        Bill bill = new Bill();
        String queryLISTMSISDN = "select AMT_BEFORE_DUE_DATE from bill_generation where deviceid='" + deviceid + "'";
        log.info("Previous Bill details--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                bill.setAmtBeforeDueDate(m_ResultSet.getFloat("AMT_BEFORE_DUE_DATE"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return bill;
    }
//getting smsPlan information

    public SmsPlan getSmsPlanInfo(long deviceid) {
        SmsPlan smsPlan = new SmsPlan();
        String queryLISTMSISDN = "select sms_amount from sms_plan where sms_plan in"
                + "(select sms_plan from assign_plan where DEVICEID='" + deviceid + "')";
        log.info("Sms Plan Details--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                smsPlan.setSmsAmount(m_ResultSet.getFloat("SMS_AMOUNT"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return smsPlan;
    }

    public Payment getPaymentInfo(long deviceid) {
        Payment payment = new Payment();
        String queryLISTMSISDN = "select RECEIVED_AMOUNT,DISCOUNT,COUPON_DISCOUNT from payment "
                + "where PAYMENT_ID='" + deviceid + "'";
        log.info("Payment Details Details--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                payment.setReceivedAmt(m_ResultSet.getFloat("RECEIVED_AMOUNT"));
                payment.setDiscount(m_ResultSet.getFloat("DISCOUNT"));
                payment.setCouponDiscount(m_ResultSet.getFloat("COUPON_DISCOUNT"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return payment;
    }
}
