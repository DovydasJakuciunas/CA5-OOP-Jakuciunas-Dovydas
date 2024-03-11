package org.example.DAOs;

import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlInfoDao extends MySqlDao implements InfoDaoInterface
{

    public List<Game_Information> findAllUsers() throws DaoException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Game_Information> gameInfoList = new ArrayList<>();

        try{
            connection = this.getConnection();

            String query = "SELECT * FROM gameinformation";
            ps = connection.prepareStatement(query);

            resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                int gameId = resultSet.getInt("GameID");
                String gameName = resultSet.getString("Game_Name");
                String gameCon = resultSet.getString("Game_Console");
                String gamePub = resultSet.getString("Game_Publisher");
                String gameDev = resultSet.getString("Game_Developer");
                String gameFra = resultSet.getString("Game_Franchise");
                String releaseDate = resultSet.getString("Game_releaseDate");
                boolean multiplayer = resultSet.getBoolean("Multiplayer");
                int playerAmount = resultSet.getInt("Player_Amount");
                int reviewScore = resultSet.getInt("Review_Score");
                Game_Information gI = new Game_Information(gameId,gameName,gameCon,gamePub,gameDev,gameFra,releaseDate,multiplayer,playerAmount,reviewScore);
                gameInfoList.add(gI);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllGameInfo() " + e.getMessage());
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }

        return gameInfoList;
    }
}
