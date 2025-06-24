



/*
 * To change this license header, choose License Headers in Project Properties.






 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */






package dao;


import au.com.bytecode.opencsv.CSVReader;


import entity.*;






import java.io.*;





import java.sql.*;

/**
 *


 * @author Tommy
 */



public class CSV {
    public static void readUserCSV() throws FileNotFoundException, IOException {



        CSVReader reader = new CSVReader(new FileReader("D:/data-v2/demographics.csv"), ',', '\'', 1);
        String[] nextLine;




 
        /**







         * @param conn
         * @param pstmt
         * @param rs
         */
        Connection conn = null;

        PreparedStatement pstmt = null;


        ResultSet rs = null;
        int batchSize = 1000;
        int count = 0;
 
        try {









            conn = ConnectionManager.getConnection();


            conn.setAutoCommit(false);
            /*pstmt = conn.prepareStatement("load data infile 'd:/unzip/location.csv'\n"



             * + "into table userlocation\n"
             * + "FIELDS TERMINATED BY ',' IGNORE 1 LINES;");*/
            pstmt = conn.prepareStatement("TRUNCATE TABLE user;");
            pstmt.executeUpdate();



            pstmt = conn.prepareStatement("INSERT INTO user (macAdd, name, password, email, gender) VALUES (?, ?, ?, ?, ?)");
            while ((nextLine = reader.readNext()) != null) {
                pstmt.setString(1, nextLine[0]);
                pstmt.setString(2, nextLine[1]);
                pstmt.setString(3, nextLine[2]);
                pstmt.setString(4, nextLine[3]);
                pstmt.setString(5, nextLine[4]);
                pstmt.addBatch();
 




                if (++count % batchSize == 0) {

                    pstmt.executeBatch();



                    //System.out.println(count);
                }
            }
            pstmt.executeBatch();













            conn.commit();
 






        } catch (SQLException e) {
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }
 
    public static void readUserLocationCSV() throws FileNotFoundException, IOException {












        CSVReader reader = new CSVReader(new FileReader("D:/data-v2/location.csv"), ',', '\'', 1);
        String[] nextLine;
 
        /**









         * @param conn
         * @param pstmt
         * @param rs










         */









        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        int batchSize = 1000;


        int count = 0;
 
        try {

            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);
            /*pstmt = conn.prepareStatement("load data infile 'd:/unzip/location.csv'\n"
             * + "into table userlocation\n"
             * + "FIELDS TERMINATED BY ',' IGNORE 1 LINES;");*/
            pstmt = conn.prepareStatement("TRUNCATE TABLE userlocation;");
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("INSERT INTO userlocation (locationID, macAdd, timestamp) VALUES (?, ?, ?)");
            while ((nextLine = reader.readNext()) != null) {
                pstmt.setInt(1, Integer.parseInt(nextLine[2]));
                pstmt.setString(2, nextLine[1]);
                pstmt.setString(3, nextLine[0]);








                pstmt.addBatch();
 


                if (++count % batchSize == 0) {






                    pstmt.executeBatch();




                    //System.out.println(count);
                }
            }
            pstmt.executeBatch();



            conn.commit();







 
        } catch (SQLException e) {
        } finally {





            ConnectionManager.close(conn, pstmt, rs);






        }





    }


    










    private static void readLocationCSV() throws FileNotFoundException, IOException{



        CSVReader reader = new CSVReader(new FileReader("D:/data-v2/location-lookup.csv"), ',', '\'', 1);







        String[] nextLine;
 



        /**
         * @param conn
         * @param pstmt
         * @param rs












         */
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int batchSize = 1000;
        int count = 0;
 
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);
            /*pstmt = conn.prepareStatement("load data infile 'd:/unzip/location.csv'\n"
             * + "into table userlocation\n"


             * + "FIELDS TERMINATED BY ',' IGNORE 1 LINES;");*/
            pstmt = conn.prepareStatement("TRUNCATE TABLE location;");
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("INSERT INTO location (locationID, semanticPlace) VALUES (?, ?)");
            while ((nextLine = reader.readNext()) != null) {
                pstmt.setInt(1, Integer.parseInt(nextLine[0]));
                pstmt.setString(2, nextLine[1]);
                pstmt.addBatch();
 
                if (++count % batchSize == 0) {
                    pstmt.executeBatch();
                    //System.out.println(count);
                }
            }
            pstmt.executeBatch();
            conn.commit();
 
        } catch (SQLException e) {
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException {
        readUserLocationCSV();
    }
}
