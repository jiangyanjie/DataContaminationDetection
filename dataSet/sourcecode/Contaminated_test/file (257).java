package PacmanProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClass {

    Connection databaseConnection = null;
    Statement statement = null;

    // ResultSet resultSet;// =
    // statement.executeQuery("SELECT * FROM HIGH_SCORE2");

    // These are for Testing
    // String name = "mewwwww";
    // int scorez = 200;

    /**
     * Default constructor for DatabaseClass
     */
    public DatabaseClass() {
        // try
        // {
        // //Establish a connection to the database
        // //databaseConnection =
        // DriverManager.getConnection("jdbc:derby:\\AdamDatabase");
        // System.out.println("Yay, connection successful!");
        // statement = databaseConnection.createStatement();
        // // resultSet = statement.executeQuery("SELECT * FROM HIGH_SCORE2");
        // // resultSet.next();
        // }
        // catch (SQLException exception)
        // {
        // exception.printStackTrace();
        // }
    }

    /**
     * Creates a new table, this method does NOT need to be called after one
     * time.
     */
    public void doConnect() {

        try {

            boolean successful = statement
                    .execute("CREATE TABLE HIGH_SCORE (NAME VARCHAR(30), SCORE INT)");
            successful = statement
                    .execute("INSERT INTO HIGH_SCORE VALUES ('Champion', 1000)");

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            // try
            // {
            // databaseConnection.close();
            // System.out.println("Yay, database disconnected");
            // }
            // catch (SQLException exception)
            // {
            // exception.printStackTrace();
            // }
        }

    }

    /**
     * This method updates the entry in the database with a new score and name
     * 
     * @param newScore is the new score to be added
     * @param newName is the new name to be added
     */
    public void setNew(int newScore, String newName) {
        try {
            // resultSet.next();
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("UPDATE HIGH_SCORE SET NAME = ?, SCORE = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newScore);
            boolean successful2 = preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        // name = newName;
        // scorez = newScore;

    }

    /**
     * Closes the connection to the database
     */
    public void closeConnection() {
        try {
            databaseConnection.close();
            System.out.println("Yay, database disconnected");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets the high score from the database
     * 
     * @return is the SCORE from the database
     */
    public int getHighScore() {
        // try
        // {
        // ResultSet resultSet =
        // statement.executeQuery("SELECT SCORE FROM HIGH_SCORE");
        // resultSet.next();
        // //System.out.println(resultSet.getInt("SCORE"));
        // return resultSet.getInt("SCORE");
        // }
        // catch (SQLException exception)
        // {
        // exception.printStackTrace();
        // }
        return -1;
    }

    /**
     * Gets the name from the database of the champ
     * 
     * @return is the NAME from the database
     */
    public String getName() {
        // try
        // {
        // ResultSet resultSet =
        // statement.executeQuery("SELECT NAME FROM HIGH_SCORE");
        // resultSet.next();
        // return resultSet.getString("NAME");
        // }
        // catch (SQLException exception)
        // {
        // exception.printStackTrace();
        // }
        return "Failed to get Name";
    }
}
