package com.ccapps.android.hextd.metagame;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/20/12
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    private int points;
    private int monies;

    public Player() {
        points = monies = 0;
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
}
