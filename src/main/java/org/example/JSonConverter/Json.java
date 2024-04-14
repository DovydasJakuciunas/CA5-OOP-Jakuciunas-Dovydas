package org.example.JSonConverter;

import com.google.gson.Gson;
import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.util.List;

public class Json {
    //FUNCTION 7  -- GameList to Json
    //USED EOIN HAMILL AS A REFERENCE

    public String gameListToJson(List<Game_Information> list) throws DaoException
    {
        Gson gsonParser = new Gson();
        String gameInfoJson = gsonParser.toJson(list);
        return gameInfoJson;

    }
    //Function 8 -- Single Game to Json
    //USED EOIN HAMILL AS A REFERENCE

    public static String singleGameToJson(Game_Information game) throws DaoException {
        Gson gsonParser = new Gson();
        String gameInfoJson = gsonParser.toJson(game);
        return gameInfoJson;
    }
}
