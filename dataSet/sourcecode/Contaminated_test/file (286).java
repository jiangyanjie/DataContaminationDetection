/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Javier
 */
public class DatabaseUpdater implements Runnable
{

    @Override
    public void run()
    {
        returnOverdueBooks();
        updateReservedBooks();
    }

    private void returnOverdueBooks()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;user=sa;password=nopw");
            Statement st = con.createStatement();

            String query = "UPDATE [HardCover].[dbo].[Book]"
                    + "SET NumCopies = NumCopies+1"
                    + "FROM [HardCover].[dbo].[CheckedOutBook] CheckedOutBook, [HardCover].[dbo].[Book] Book "
                    + "WHERE ExpirationDate <= GETDATE() AND CheckedOutBook.BookId = Book.BookUuid AND CheckedOutBook.Expired = 0";
            st.executeUpdate(query);
            query = "UPDATE [HardCover].[dbo].[CheckedOutBook] "
                    + "SET Expired = 1 "
                    + "WHERE ExpirationDate <= GETDATE()";
            st.executeUpdate(query);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void updateReservedBooks()
    {
        try
        {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;user=sa;password=nopw");
            Statement st = con.createStatement();

            String query = "DECLARE @resBookId int "
                    + "DECLARE @regUserId UNIQUEIDENTIFIER "
                    + "DECLARE @bookId UNIQUEIDENTIFIER "
                    + "DECLARE @expirationDate DATETIME "
                    + "SET @expirationDate = DATEADD(day, 10, GETDATE()) "
                    + "DECLARE db_cursor CURSOR FOR "
                    + "SELECT rBook.ReservedBookId, rBook.RegisteredUserId, rBook.BookId "
                    + "FROM [HardCover].[dbo].[ReservedBook] rBook, [HardCover].[dbo].[Book] Book "
                    + "WHERE rBook.BookId = Book.BookUuid AND Book.Active = 1 AND Book.NumCopies > 0 "
                    + "ORDER BY rBook.ReserveDate DESC "
                    + "OPEN db_cursor "
                    + "FETCH NEXT FROM db_cursor INTO @resBookId, @regUserId, @bookId "
                    + "WHILE @@FETCH_STATUS = 0 "
                    + "BEGIN "
                    + "INSERT INTO [HardCover].[dbo].[CheckedOutBook](RegisteredUserId, BookId, ExpirationDate, Expired) "
                    + "VALUES(@regUserId, @bookId, @expirationDate, 0); "
                    + "DELETE FROM [HardCover].[dbo].[ReservedBook] "
                    + " WHERE ReservedBookId = @resBookId; "
                    + "UPDATE [HardCover].[dbo].[Book] "
                    + "SET NumCopies = NumCopies-1, TimesBorrowed = TimesBorrowed+1 "
                    + "WHERE BookUuid = @bookId "
                    + " FETCH NEXT FROM db_cursor INTO @resBookId, @regUserId, @bookId "
                    + "END "
                    + "CLOSE db_cursor "
                    + "DEALLOCATE db_cursor ";
            st.executeQuery(query);
            System.out.println("omg got here");

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
