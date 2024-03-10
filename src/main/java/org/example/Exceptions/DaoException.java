package org.example.Exceptions;

import java.sql.SQLException;

public class DaoException extends SQLException {

    public DaoException(){
        //not Used yet
    }

    public DaoException(String aMessage)
    {
        super(aMessage);
    }
}
