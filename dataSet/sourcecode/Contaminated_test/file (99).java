/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zgpdistribution.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import zgpdistribution.util.oops.CustomerType;

/**
 *
 * @author John
 */
public class CustomerTypeDAO {

    private Connection conn;

    public CustomerTypeDAO() {
        try {
            conn = DataSourceConnection.initDB();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean save(CustomerType data) {
        String sql = "insert into customerlist (name, code) value (?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, data.getName());
            ps.setString(2, data.getCode());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<CustomerType> queryAll() {
        String sql = "select * from customerlist order by name asc";
        ArrayList<CustomerType> custTypeList = null;
        try {
            custTypeList = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                custTypeList.add(new CustomerType(rs.getString("name"), rs.getString("code")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return custTypeList;
    }
}
