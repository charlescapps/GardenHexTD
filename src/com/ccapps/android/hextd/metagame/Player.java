package com.ccapps.android.hextd.metagame;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class Player {

    private static Player playerInstance;

    public static Player getInstance() {
        if (playerInstance != null) {
            return playerInstance;
        }
        playerInstance = new Player(0, 400, 50);
        return playerInstance;
    }

    private int points;
    private int monies;
    private int life;

    private Player(int points, int monies, int life) {
        this.points = points;
        this.monies = monies;
        this.life = life;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getMonies() {
        return monies;
    }

    public void addMonies(int monies) {
        this.monies += monies;
    }

    public void spendMonies(int monies) {
        this.monies = Math.max(0, this.monies - monies);
    }

    public int getLife() {
        return life;
    }

    public void lostLife(int lost) {
        this.life -= lost;
    }
}
//CLC: Original Code End
