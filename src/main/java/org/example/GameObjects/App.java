package org.example.GameObjects;

import org.example.DAOs.InfoDaoInterface;
import org.example.DAOs.MySqlInfoDao;
import org.example.DTOs.Game_Information;
import org.example.Exceptions.DaoException;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class App {
    public static void main(String[] args) throws DaoException {


        Scanner in = new Scanner(System.in);

        try{
            System.out.println("Welcome to the game index!!!!");
            System.out.println("For guiding within the index. Numbers 1 - ?? , 0 is exit");
            System.out.println("How many I help you today");

            int usersChoice = in.nextInt();

            while( usersChoice != 0)
            {
                ShowMenu();
                int continueOn = in.nextInt();
                ChosenOption(continueOn, in);

            }


        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private static int ChosenOption(int usersChoice, Scanner in) throws DaoException {
        InfoDaoInterface IInfoDao = new MySqlInfoDao();
        List<Game_Information> gameInfo = IInfoDao.findAllGames();


        if (usersChoice == 1){
            FindAllGameInfo(gameInfo,IInfoDao);
            return 0;
        }
        if (usersChoice ==2){
            System.out.println("Enter the id of the game");
            FindGameByID(IInfoDao,in);
            return 0;
        }
        if (usersChoice ==3 ) {
            System.out.println("Which game would you like to delete");
            DeleteGameByID(IInfoDao,gameInfo,in);
            return 0;
        }
        if (usersChoice == 0)
        {
            System.exit(0);
        }


        //FindGameByID(IInfoDao, in);

        //DeleteGameByID(IInfoDao, gameInfo, in);

        //AddNewGame(IInfoDao, in);

        return 0;
    }

    private static void ShowMenu()
    {
        System.out.println("1. Find all games within the index");
        System.out.println("2. Find game by ID");
        System.out.println("3. Delete game by ID");
        System.out.println("4. Register a new game");
        System.out.println("0. Exit");


    }

    private static void AddNewGame(InfoDaoInterface IInfoDao, Scanner in) throws DaoException {
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
        System.out.println(IInfoDao.findAllGames()+"\n");

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
