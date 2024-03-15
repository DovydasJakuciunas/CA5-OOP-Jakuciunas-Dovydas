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
    @Override
    public List<Game_Information> findAllGames() throws DaoException {
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

    @Override
    public Game_Information findGameById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Game_Information idGame = null;

        try{
            connection = this.getConnection();

            String query = "SELECT * FROM gameinformation WHERE GAMEID = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
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

                idGame  = new Game_Information(gameId,gameName,gameCon,gamePub,gameDev,gameFra,releaseDate,multiplayer,playerAmount,reviewScore);
            }

        } catch (SQLException e) {
            throw new DaoException("findGameByID() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findGameByID() " + e.getMessage());
            }
        }

        return idGame;
    }

    @Override
    public int deleteGameById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsUpdated = 0;

        try {
            connection = getConnection();
            String query = "delete from gameinformation where GAMEID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rowsUpdated = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException("Delete Game by Id failed " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        return 0;
    }

    @Override
    public Game_Information registerGame(int GameId, String Game_name, String Game_console, String Game_publisher, String Game_developer, String Game_franchise, String Game_releasedate, boolean Multiplayer, int Player_amount, int Review_Score) throws DaoException {
        Game_Information gm = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = getConnection();
            String query = "insert into gameinformation(GAMEID, GAME_NAME, GAME_CONSOLE,GAME_PUBLISHER, GAME_DEVELOPER,GAME_FRANCHISE,GAME_RELEASEDATE,MULTIPLAYER, PLAYER_AMOUNT, REVIEW_SCORE)" + "values(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,GameId);
            preparedStatement.setString(2,Game_name);
            preparedStatement.setString(3,Game_console);
            preparedStatement.setString(4,Game_publisher);
            preparedStatement.setString(5,Game_developer);
            preparedStatement.setString(6,Game_franchise);
            preparedStatement.setString(7,Game_releasedate);
            preparedStatement.setBoolean(8,Multiplayer);
            preparedStatement.setInt(9,Player_amount);
            preparedStatement.setInt(10,Review_Score);

            int id = preparedStatement.executeUpdate();
            gm = new Game_Information();
            gm.setGameId(id);
            gm.setGame_name(Game_name);
            gm.setGame_console(Game_console);
            gm.setGame_publisher(Game_publisher);
            gm.setGame_developer(Game_developer);
            gm.setGame_franchise(Game_franchise);
            gm.setGame_releasedate(Game_releasedate);
            gm.setMultiplayer(Multiplayer);
            gm.setPlayer_amount(Player_amount);
            gm.setReview_Score(Review_Score);

        } catch (SQLException e)
        {
            throw new DaoException("Error adding Game: "+ e.getMessage());
        }
        finally {
            try{
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    @Override
    public Game_Information updateGameById(int id, Game_Information game) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsUpdated = 0;

        try{
            connection = getConnection();
            String query = "update gameinformation set  GAME_NAME = ?, GAME_CONSOLE =?, GAME_PUBLISHER=?,GAME_DEVELOPER = ?,GAME_FRANCHISE = ?,MULTIPLAYER = ?, PLAYER_AMOUNT = ?,REVIEW_SCORE = ? where GAMEID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, game.getGame_name());
            preparedStatement.setString(2,game.getGame_console());
            preparedStatement.setString(3, game.getGame_publisher());
            preparedStatement.setString(4, game.getGame_developer());
            preparedStatement.setString(5, game.getGame_franchise());
            preparedStatement.setString(6, game.getGame_releasedate());
            preparedStatement.setBoolean(7,game.isMultiplayer());
            preparedStatement.setInt(8,game.getPlayer_amount());
            preparedStatement.setInt(9,game.getReview_Score());
            rowsUpdated = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException("Update Game Information failed " + e.getMessage());
        } finally {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        return game;
    }


}
