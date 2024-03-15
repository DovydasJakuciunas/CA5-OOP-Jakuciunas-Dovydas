package org.example.DAOs;



import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySqlDao
{
    public Connection getConnection() throws DaoException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/game_information";
        String username = "root";
        String password = "";
        Connection connection = null;

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        }
        catch (SQLException e)
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

    //int GameId, String Game_name, String Game_console, String Game_publisher, String Game_developer, String Game_franchise, String Game_releasedate, boolean Multiplayer, int Player_amount, int Review_Score

}
