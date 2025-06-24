package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Book;

/**
 *
 * @author Kevin Ryan
 */

public class DAOSQLite {

    protected final static String DRIVER = "org.sqlite.JDBC";
    protected final static String JDBC = "jdbc:sqlite";

    public static void insertRecord(Book book, String dbPath) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnectionDAO(dbPath);
            String q = "insert into bookhistory (id, semester, email, course, "
                    + "isbn, amount) values (null, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(q);
            ps.setString(1, book.getSemester());
            ps.setString(2, book.getEmail());
            ps.setString(3, book.getCourse());
            ps.setString(4, book.getIsbn());
            ps.setInt(5, book.getAmount());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
    }

    public static void deleteRecordById(int id, String dbPath) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnectionDAO(dbPath);
            String q = "delete from bookhistory where id = ?";
            ps = conn.prepareStatement(q);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
    }

    public static List<Book> getAllRecordsAsList(String dbPath) {
        String q = "select * from bookhistory order by id";
        List<Book> list = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnectionDAO(dbPath);
            ps = conn.prepareStatement(q);
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
        return list;
    }
    
    public static List<Book> getAllRecordsAsList1(String course, String dbPath){
        String q = "select * from bookhistory where course =  ?";
        List<Book> list = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnectionDAO(dbPath);
            ps = conn.prepareStatement(q);
            ps.setString(1, course);
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
        return list;
    }
   
    public static List<Book> getAllRecordsAsList2(String email, String dbPath){
        String q = "select * from bookhistory where email = ?";
        List<Book> list = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnectionDAO(dbPath);
            ps = conn.prepareStatement(q);
            ps.setString(1, email);
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
        return list;
    }

public static void createTables(String dbPath) {
        String q = "create table bookhistory ("
                + "id integer not null primary key autoincrement, "
                + "semester varchar(20) not null, "
                + "email varchar(25) not null, "
                + "course varchar(10) not null, "
                + "isbn varchat(20) not null, "
                + "amount int not null)";
        Connection conn = getConnectionDAO(dbPath);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(q);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
}
    public static void dropTables(String dbPath) {
        String q = "drop table if exists bookhistory";
        Connection conn = getConnectionDAO(dbPath);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(q);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closePS(ps);
            closeConnection(conn);
        }
    }

    public static void populateTables(String dbPath) {
        Book b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15;
        b1 = new Book(0L, "2014Fall", "jphillips@test.com", "CIS3330", "127-1-82113-1346", 8);
        b2 = new Book(0L, "2014Fall", "jphillips@test.com", "CIS3330", "127-2-82113-1346", 8);
        b3 = new Book(0L, "2014Fall", "jphillips@test.com", "CIS3330", "127-3-82113-1346", 8);
        b4 = new Book(0L, "2015Spring", "sschaar@test.com", "ART2202", "128-1-82113-1346", 20);
        b5 = new Book(0L, "2015Spring", "sschaar@test.com", "ART2202", "128-2-82113-1346", 20);
        b6 = new Book(0L, "2015Spring", "sschaar@test.com", "ART2202", "128-3-82113-1346", 20);
        b7 = new Book(0L, "2014Fall", "hiseri@test.com", "MA2202", "129-1-82113-1346", 15);
        b8 = new Book(0L, "2014Fall", "hiseri@test.com", "MA2202", "129-2-82113-1346", 15);
        b9 = new Book(0L, "2014Fall", "hiseri@test.com", "MA2202", "129-3-82113-1346", 15);
        b10 = new Book(0L, "2015Spring", "ddoolittle@test.com", "PSY2202", "130-1-82113-1346", 30);
        b11 = new Book(0L, "2015Spring", "ddoolittle@test.com", "PSY2202", "130-2-82113-1346", 30);
        b12 = new Book(0L, "2015Spring", "ddoolittle@test.com", "PSY2202", "130-3-82113-1346", 30);
        b13 = new Book(0L, "2014Fall", "inewton@test.com", "PHY2202", "131-1-82113-1346", 12);
        b14 = new Book(0L, "2014Fall", "inewton@test.com", "PHY2202", "131-2-82113-1346", 12);
        b15 = new Book(0L, "2014Fall", "inewton@test.com", "PHY2202", "131-3-82113-1346", 12);
        
        DAOSQLite.insertRecord(b1, dbPath);
        DAOSQLite.insertRecord(b2, dbPath);
        DAOSQLite.insertRecord(b3, dbPath);
        DAOSQLite.insertRecord(b4, dbPath);
        DAOSQLite.insertRecord(b5, dbPath);
        DAOSQLite.insertRecord(b6, dbPath);
        DAOSQLite.insertRecord(b7, dbPath);
        DAOSQLite.insertRecord(b8, dbPath);
        DAOSQLite.insertRecord(b9, dbPath);
        DAOSQLite.insertRecord(b10, dbPath);
        DAOSQLite.insertRecord(b11, dbPath);
        DAOSQLite.insertRecord(b12, dbPath);
        DAOSQLite.insertRecord(b13, dbPath);
        DAOSQLite.insertRecord(b14, dbPath);
        DAOSQLite.insertRecord(b15, dbPath);

    }

    protected static List<Book> myQuery(Connection conn, PreparedStatement ps) {
        List<Book> list = new ArrayList();
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String semester = rs.getString("semester");
                String email = rs.getString("email");
                String course = rs.getString("course");
                String isbn = rs.getString("isbn");
                int amount= rs.getInt("amount");
                Book p = new Book(id, semester, email, course, isbn, amount);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeRS(rs);
        }
        return list;
    }

    protected static Connection getConnectionDAO(String dbPath) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC + ":" + dbPath);
        } catch (ClassNotFoundException cnfex) {
            Logger.getLogger(DAOSQLite.class.getName()).log(Level.SEVERE, null, cnfex);
        } catch (SQLException sqlex) {
            Logger.getLogger(DAOSQLite.class.getName()).log(Level.SEVERE, null, sqlex);
        }
        return conn;
    }

    protected static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void closePS(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void closeRS(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
