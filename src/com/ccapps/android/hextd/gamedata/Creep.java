package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public interface Creep {
    public int getDirection();
    public void setDirection(int direction);

    public int getSpeed();
    public void setSpeed(int speed);

    public Drawable getCreepDrawable();
    public void setCreepDrawable(CreepDrawable drawable);

    public List<Hexagon> getPath();
    public void setPath(List<Hexagon> path);

    public Hexagon getHex();
    public void setHex(Hexagon hex);

    public Hexagon getSourceHex();
    public void setSourceHex(Hexagon hex);

    public Hexagon getGoalHex();
    public void setGoalHex(Hexagon hex);

    public int getHitpoints();
    public void setHitpoints(int hp);
    public void loseHitpoints(int hp);

    public boolean isDead();

    public CreepAlgorithm getAlgorithm();
    public void setAlgorithm(CreepAlgorithm creepAlgorithm);

    public void evaluateRoute();
    public void move();
    public void draw(Canvas canvas);

    public State getState();
    public void setState(State state);
    public Hexagon getPrevHex();

}
