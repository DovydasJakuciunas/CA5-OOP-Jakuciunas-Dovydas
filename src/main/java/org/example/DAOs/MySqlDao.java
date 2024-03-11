package org.example.DAOs;

import org.example.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao
{
    public Connection getConnection() throws DaoException
    {

        String url = "jdbc:mysql://localhost:3306/game_information";
        String username = "root";
        String password = "";
        Connection connection = null;

        try
        {

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e)
        {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return connection;
    }

    public void freeConnection(Connection connection) throws DaoException
    {
        try
        {
            if (connection != null)
            {
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
}
