package org.example.DAOs;

import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;

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

    //int GameId, String Game_name, String Game_console, String Game_publisher, String Game_developer, String Game_franchise, String Game_releasedate, boolean Multiplayer, int Player_amount, int Review_Score
    @Override
    public Game_Information registerGame(Game_Information gameInfo) throws DaoException {
        Game_Information gm= null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int newId =0;

        try{
            connection = getConnection();
            String query = "insert into gameinformation(GAMEID, GAME_NAME, GAME_CONSOLE,GAME_PUBLISHER, GAME_DEVELOPER,GAME_FRANCHISE,GAME_RELEASEDATE,MULTIPLAYER, PLAYER_AMOUNT, REVIEW_SCORE)" + "values(?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,GenerateId());
            preparedStatement.setString(2, gameInfo.getGame_name());
            preparedStatement.setString(3,gameInfo.getGame_console());
            preparedStatement.setString(4, gameInfo.getGame_publisher());
            preparedStatement.setString(5,gameInfo.getGame_developer());
            preparedStatement.setString(6,gameInfo.getGame_franchise());
            preparedStatement.setString(7,gameInfo.getGame_releasedate());
            preparedStatement.setBoolean(8,gameInfo.isMultiplayer());
            preparedStatement.setInt(9,gameInfo.getPlayer_amount());
            preparedStatement.setInt(10,gameInfo.getReview_Score());

            int id = preparedStatement.executeUpdate();
            gm = new Game_Information();
            gm.setGameId(id);
            gm.setGame_name(gameInfo.getGame_name());
            gm.setGame_console(gameInfo.getGame_console());
            gm.setGame_publisher(gameInfo.getGame_publisher());
            gm.setGame_developer(gameInfo.getGame_developer());
            gm.setGame_franchise(gameInfo.getGame_franchise());
            gm.setGame_releasedate(gameInfo.getGame_releasedate());
            gm.setMultiplayer(gameInfo.isMultiplayer());
            gm.setPlayer_amount(gameInfo.getPlayer_amount());
            gm.setReview_Score(gameInfo.getReview_Score());


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
        return gm;

    }

    @Override
    public Game_Information updateGameById(int id, Game_Information gameInfo) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try{
            connection = getConnection();
            // GAME_NAME = ?, GAME_PUBLISHER=?, GAME_DEVELOPER = ?,  GAME_FRANCHISE = ?,
            String query = "update gameinformation set  MULTIPLAYER = ? where GameId=?";


            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,gameInfo.getGameId());
            preparedStatement.setBoolean(2,gameInfo.isMultiplayer());

        }
        catch (SQLException e) {
            throw new DaoException("Update Game Information failed " + e.getMessage());
        }
        finally {

            try
            {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }


        return gameInfo;
    }

    @Override
    public int GenerateId() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement= null;
        int newId = 0;

        try{
            connection = getConnection();

            String query = "select MAX(GAMEID) from gameinformation";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery(query);
            {
                if (resultSet.next()) {
                    newId = resultSet.getInt(1) + 1; // Increment the maximum id by 1
                }

                    }


            } catch (SQLException e) {
            throw new RuntimeException("Failed to GenerateId()"+e);
        }finally
        {
            try
            {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        return newId;
    }

    //FUNCTION 6
    //AUTHOR EOIN HAMILL WROTE THIS ENTIRE METHOD
    @Override
    public List<Game_Information> FindGameUsingFilter(Comparator<Game_Information> gameNameComparator) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Game_Information> gameInfoList = new ArrayList<>();
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the Name of the game you want to find");
        String filter = kb.nextLine();

        try{

            connection = this.getConnection();
            String query = "SELECT * FROM gameinformation " ;
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
                if (gameName.equals(filter))
                {
                    System.out.println("Game Found: " + gI);
                }

            }
        } catch (SQLException e) {
            throw new DaoException("findGameUsingFilter() " + e.getMessage());
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
                throw new DaoException("findGameUsingFilter() " + e.getMessage());
            }
        }

        return gameInfoList;
    }




}