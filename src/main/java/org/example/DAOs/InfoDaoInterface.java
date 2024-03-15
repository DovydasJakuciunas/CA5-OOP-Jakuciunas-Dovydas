package org.example.DAOs;

import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.util.List;

public interface InfoDaoInterface {

    public List<Game_Information> findAllGames() throws DaoException;

    public Game_Information findGameById(int id) throws DaoException;
    public int deleteGameById(int id) throws DaoException;
    public Game_Information registerGame( int GameId,String Game_name,String Game_console, String Game_publisher, String Game_developer, String Game_franchise, String Game_releasedate, boolean Multiplayer, int Player_amount, int Review_Score) throws DaoException;

    public Game_Information updateGameById(int id, Game_Information game) throws DaoException;
}
