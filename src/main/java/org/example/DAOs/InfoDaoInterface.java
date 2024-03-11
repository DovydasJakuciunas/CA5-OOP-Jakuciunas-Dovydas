package org.example.DAOs;

import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.util.List;

public interface InfoDaoInterface {

    public List<Game_Information> findAllUsers() throws DaoException;
}
