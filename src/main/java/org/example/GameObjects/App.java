package org.example.GameObjects;

import org.example.DAOs.InfoDaoInterface;
import org.example.DAOs.MySqlInfoDao;
import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws DaoException {
        InfoDaoInterface IInfoDao = new MySqlInfoDao();

        Scanner in = new Scanner(System.in);

        try{
            System.out.println("\n Call FindAllGameInfo()");
            List<Game_Information> gameInfo = IInfoDao.findAllUsers();

            printInfo(gameInfo);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private static void printInfo(List<Game_Information> gameInfo) {
        if( gameInfo.isEmpty() )
            System.out.println("There are no Users");
        else {
            for (Game_Information user : gameInfo)
                System.out.println("User: " + user.toString());
        }
    }
}
