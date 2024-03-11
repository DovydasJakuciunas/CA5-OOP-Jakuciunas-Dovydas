package org.example.GameObjects;

import org.example.DAOs.InfoDaoInterface;
import org.example.DAOs.MySqlInfoDao;
import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws DaoException {
        InfoDaoInterface IInfoDao = new MySqlInfoDao();
        List<Game_Information> gameInfo = IInfoDao.findAllGames();

        Scanner in = new Scanner(System.in);

        try{
            FindAllGameInfo(gameInfo,IInfoDao);

            //FindGameByID(IInfoDao, in);

            //DeleteGameByID(IInfoDao, gameInfo, in);

            //Feature 4 – Insert an Entity
            System.out.println("\n Call registerGame()");
            System.out.println("Game ID ");
            int gameIdIn = in.nextInt();

            System.out.println("Game Name ");
            String gameNameIn = in.next();

            System.out.println("Console Where Game Began ");
            String gameConsoleIn = in.next();

            System.out.println("Publisher of The Game ");
            String gamePubIn = in.next();

            System.out.println("Developer of The Game ");
            String gameDevIn = in.next();

            System.out.println("Franchise which it belongs to ");
            String gameFraIn = in.next();

            System.out.println("Release Date ");
            String gameDateIn = in.next();

            System.out.println("Is it Multiplayer");
            boolean multiIn = in.nextBoolean();

            System.out.println("Player Amount (Counted in the thousands, Whole Numbers)");
            int playerIn = in.nextInt();

            System.out.println("Review of game(Whole number from 0-100)");
            int reviewIn = in.nextInt();

            Game_Information rowsUpdated = IInfoDao.registerGame(gameIdIn,gameNameIn,gameConsoleIn,gamePubIn,gameDevIn,gameFraIn,gameDateIn,multiIn,playerIn,reviewIn);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteGameByID(InfoDaoInterface IInfoDao, List<Game_Information> gameInfo, Scanner in) throws DaoException {
        //Feature 3 – Delete an Entity by key
        System.out.println("\n Delete Game by ID of your choice");
        int id = in.nextInt();
        in.nextLine();
        int rowsAffected = IInfoDao.deleteGameById(id);
        System.out.println("Rows Deleted "+ rowsAffected);
        printInfo(gameInfo);
    }

    private static void FindAllGameInfo(List<Game_Information> gameInfo,InfoDaoInterface IInfoDao) throws DaoException {
        //Function 1 – Get all Entities
        System.out.println("\n Call FindAllGameInfo()");
        printInfo(gameInfo);
    }

    private static void FindGameByID(InfoDaoInterface IInfoDao, Scanner in) throws DaoException {
        //Function 2 - – Find and Display (a single) Entity by Key
        System.out.println(" \n Call findGameById() of your Id choice");
        int gameId = in.nextInt();
        Game_Information gm = IInfoDao.findGameById(gameId);
        System.out.println(gm);
    }

    private static void printInfo(List<Game_Information> gameInfo) {
        if( gameInfo.isEmpty() )
            System.out.println("There is no Game anymore");
        else {
            for (Game_Information Game : gameInfo)
                System.out.println("Game: " + Game.toString());
        }
    }
}
