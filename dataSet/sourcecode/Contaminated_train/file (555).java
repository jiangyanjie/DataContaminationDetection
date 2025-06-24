/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hkdvm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijin
 */
public class ConnectDB {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase(Data d) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                    + "user=root&password=root");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement("insert into  data.table values (?, ?, ?, ?, ? , ?, ?, ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            // Parameters start with 1
            preparedStatement.setString(1, d.getTrialName());
            preparedStatement.setString(2, d.getGeneSymbol());
            preparedStatement.setInt(3, d.getPatientId());
            preparedStatement.setString(4, d.getProbeSet());
            preparedStatement.setDouble(5, d.getpValue());
            preparedStatement.setString(6, d.getSubjectId());
            preparedStatement.setDouble(7, d.getRawIntensity());
            preparedStatement.setDouble(8, d.getLogIntensity());
            preparedStatement.setDouble(9, d.getzScore());
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    public void selectDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                    + "user=root&password=root");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            System.out.println("before start");
            long startTime = System.nanoTime();

            preparedStatement = connect.prepareStatement("SELECT * from `data`.`table` where trial_name = 'MULTMYEL' and patient_id >= 79109 and patient_id <= 79667");
            resultSet = preparedStatement.executeQuery();
            System.out.println("start fetching");
            int count = 0;
            while (resultSet.next()) {
                count++;
                if (count % 500000 == 0) {
                    System.out.println(count);
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("total time is " + duration);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
    /*
    public static void main(String[] args) {
    try {
    ConnectDB db = new ConnectDB();
    db.selectDataBase();
    } catch (Exception ex) {
    Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

     */

    public void selectDataBase1() throws Exception {
        Connection conn = null;
        Statement stmt = null;
        int start = 79109;
        int end = 79667;

        long ts = System.currentTimeMillis();

        int max = 6;

        for (int i = 1; i <= max; i++) {

            try {
                conn = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                        + "user=root&password=root");
                stmt = conn.createStatement();

                int temp = 0;
                if (i == max) {
                    temp = end;
                } else {
                    temp = start + 100;
                }

                System.out.println("start " + start);
                System.out.println("end " + temp);
                String sql = "SELECT  GENE_SYMBOL, PATIENT_ID, PROBESET, SUBJECT_ID, RAW_INTENSITY from `data`.`table` where trial_name = 'MULTMYEL' and patient_id >= " + start + " and patient_id <= " + temp;
                start = temp + 1;
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("start");
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count % 500000 == 0) {
                        System.out.println(count);
                    }
                }
                System.out.println("total number is " + count);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("total time is " + (System.currentTimeMillis() - ts));
    }

    public void selectDataBase2() throws Exception {
        Connection conn = null;
        Statement stmt = null;
        int start = 79109;
        int end = 79667;

        int max = 6;

        for (int i = 1; i <= max; i++) {

            try {
                conn = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                        + "user=root&password=root");
                stmt = conn.createStatement();

                System.out.println("i: " + i);
                int temp = 0;
                if (i == 1) {
                    temp = start + 35 - 1;
                } else if (i == 2) {
                    temp = start + 70 - 1;
                } else if (i == 3) {
                    temp = start + 105 - 1;
                } else if (i == 4) {
                    temp = start + 140 - 1;
                } else if (i == 5) {
                    temp = start + 280 - 1;
                } else {
                    temp = start + 559 - 1;
                }

                System.out.println("start " + start);
                System.out.println("end " + temp);
                long ts = System.currentTimeMillis();
                String sql = "SELECT  GENE_SYMBOL, PATIENT_ID, PROBESET, SUBJECT_ID, RAW_INTENSITY from `data`.`table` where trial_name = 'MULTMYEL' and patient_id >= " + start + " and patient_id <= " + temp;
                //start = temp + 1;
                ResultSet rs = stmt.executeQuery(sql);

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count % 500000 == 0) {
                        System.out.println(count);
                    }
                }
                System.out.println("total number is " + count);
                System.out.println("total time is " + (System.currentTimeMillis() - ts));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void select35() throws Exception {
        Connection conn = null;
        Statement stmt = null;
        int start = 79109;
        int end = 79667;

        //    int max = 6;

        //    for (int i = 1; i <= max; i++) {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                    + "user=root&password=root");
            stmt = conn.createStatement();

//                System.out.println("i: " + i);
//                int temp = 0;
//                if (i == 1) {
//                    temp = start + 35 - 1;
//                } else if (i == 2) {
//                    temp = start + 70 - 1;
//                } else if (i == 3) {
//                    temp = start + 105 - 1;
//                } else if (i == 4) {
//                    temp = start + 140 - 1;
//                } else if (i == 5) {
//                    temp = start + 280 - 1;
//                } else {
//                    temp = start + 559 - 1;
//                }

            int temp = start + 35 - 1;
            System.out.println("start " + start);
            System.out.println("end " + temp);
            long ts = System.currentTimeMillis();
            String sql = "SELECT  GENE_SYMBOL, PATIENT_ID, PROBESET, SUBJECT_ID, RAW_INTENSITY from `data`.`table` where trial_name = 'MULTMYEL' and patient_id >= " + start + " and patient_id <= " + temp;
            //start = temp + 1;
            ResultSet rs = stmt.executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                count++;
                if (count % 500000 == 0) {
                    System.out.println(count);
                }
            }
            System.out.println("total number is " + count);
            System.out.println("total time is " + (System.currentTimeMillis() - ts));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //  }


    }

    public void select(int b) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        int start = 79109;
        int end = 79667;

        //    int max = 6;

        //    for (int i = 1; i <= max; i++) {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                    + "user=root&password=root");
            stmt = conn.createStatement();

//                System.out.println("i: " + i);
//                int temp = 0;
//                if (i == 1) {
//                    temp = start + 35 - 1;
//                } else if (i == 2) {
//                    temp = start + 70 - 1;
//                } else if (i == 3) {
//                    temp = start + 105 - 1;
//                } else if (i == 4) {
//                    temp = start + 140 - 1;
//                } else if (i == 5) {
//                    temp = start + 280 - 1;
//                } else {
//                    temp = start + 559 - 1;
//                }

            int temp = start + b - 1;
            System.out.println("start " + start);
            System.out.println("end " + temp);
            long ts = System.currentTimeMillis();
            String sql = "SELECT  GENE_SYMBOL, PATIENT_ID, PROBESET, SUBJECT_ID, RAW_INTENSITY from `data`.`table` where trial_name = 'MULTMYEL' and patient_id >= " + start + " and patient_id <= " + temp;
            //start = temp + 1;
            ResultSet rs = stmt.executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                count++;
                if (count % 500000 == 0) {
                    System.out.println(count);
                }
            }
            System.out.println("total number is " + count);
            System.out.println("total time is " + (System.currentTimeMillis() - ts));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //  }


    }

    public void newsSelect(int number, int size) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://146.169.35.107/data?"
                    + "user=root&password=root");
            stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
              java.sql.ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(Integer.MIN_VALUE);
            long ts = System.currentTimeMillis();
            Case c = new Case();
            String sql = c.getCase(number);

            //  System.out.println(sql);
            //start = temp + 1;
            ResultSet rs = stmt.executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                count++;
                if (count % 500000 == 0) {
                    System.out.println(count);
                }
            }
            System.out.println("total number is " + count);

            System.out.println("total time is " + (System.currentTimeMillis() - ts));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //  }


    }

    public static void main(String[] args) {
        try {
            ConnectDB db = new ConnectDB();

            int count = Integer.parseInt(args[1]);

            for (int i = 0; i < count; i++) {
                db.newsSelect(Integer.parseInt(args[0]), Integer.parseInt(args[2]));
            }
        } catch (Exception ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
