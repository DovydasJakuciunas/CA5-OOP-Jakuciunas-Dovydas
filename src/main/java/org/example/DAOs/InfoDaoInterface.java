package org.example.DAOs;

import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public interface InfoDaoInterface {

    public List<Game_Information> findAllGames() throws DaoException;

    public Game_Information findGameById(int id) throws DaoException;
    public int deleteGameById(int id) throws DaoException;
    public Game_Information registerGame(Game_Information gameInfo) throws DaoException;

    public Game_Information updateGameById(int id, Game_Information game) throws DaoException;

    public int GenerateId() throws DaoException;
    public List<Game_Information> FindGameUsingFilter(Comparator<Game_Information> gameNameComparator) throws SQLException;

    public String playerListToJson(List<Game_Information> list) throws DaoException;
}
