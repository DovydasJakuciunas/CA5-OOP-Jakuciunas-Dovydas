package org.example.DTOs;

public class Game_Information
{
    private int GameId;
    private String Game_name;
    private String Game_console;
    private String Game_publisher;
    private String Game_developer;
    private String Game_franchise;
    private String Game_releasedate;
    private boolean Multiplayer;
    private int Player_amount;
    private int Review_Score;

    // Empty Con
    public Game_Information() {
    }


    //Full Con

    public Game_Information(int gameId, String game_name, String game_console, String game_publisher, String game_developer, String game_franchise, String game_releasedate, boolean multiplayer, int player_amount, int review_Score) {
        GameId = gameId;
        Game_name = game_name;
        Game_console = game_console;
        Game_publisher = game_publisher;
        Game_developer = game_developer;
        Game_franchise = game_franchise;
        Game_releasedate = game_releasedate;
        Multiplayer = multiplayer;
        Player_amount = player_amount;
        Review_Score = review_Score;
    }

    public int getGameId() {
        return GameId;
    }
    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public String getGame_name() {
        return Game_name;
    }
    public void setGame_name(String game_name) {
        Game_name = game_name;
    }

    public String getGame_publisher() {
        return Game_publisher;
    }
    public void setGame_publisher(String game_publisher) {
        Game_publisher = game_publisher;
    }

    public String getGame_console() {
        return Game_console;
    }
    public void setGame_console(String game_console) {
        Game_console = game_console;
    }

    public String getGame_developer() {
        return Game_developer;
    }
    public void setGame_developer(String game_developer) {
        Game_developer = game_developer;
    }

    public String getGame_franchise() {
        return Game_franchise;
    }
    public void setGame_franchise(String game_franchise) {
        Game_franchise = game_franchise;
    }

    public String getGame_releasedate() {
        return Game_releasedate;
    }
    public void setGame_releasedate(String game_releasedate) {
        Game_releasedate = game_releasedate;
    }

    public boolean isMultiplayer() {
        return Multiplayer;
    }
    public void setMultiplayer(boolean multiplayer) {
        Multiplayer = multiplayer;
    }

    public int getPlayer_amount() {
        return Player_amount;
    }
    public void setPlayer_amount(int player_amount) {
        Player_amount = player_amount;
    }

    public int getReview_Score() {
        return Review_Score;
    }
    public void setReview_Score(int review_Score) {
        Review_Score = review_Score;
    }

    @Override
    public String toString() {
        return "Game_Information{" +
                "GameId=" + GameId +
                ", Game_name='" + Game_name + '\'' +
                ", Game_console='" + Game_console + '\'' +
                ", Game_developer='" + Game_developer + '\'' +
                ", Game_franchise='" + Game_franchise + '\'' +
                ", Game_releasedate='" + Game_releasedate + '\'' +
                ", Multiplayer=" + Multiplayer +
                ", Player_amount=" + Player_amount +
                ", Review_Score=" + Review_Score +
                '}';
    }
}
