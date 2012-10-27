package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.PointF;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/29/12
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Tower {
    /***********GETTERS / SETTERS***************/
    public Hexagon getHex();
    public int getDmgPerAttack();
    public List<Hexagon> getAttackHexes(); //Read-only, not set directly
    public int getAttackSpeed();
    public TowerDrawable getTowerDrawable();
    public int getDirection();
    public void setDirection(int direction);
    public String getDamageString();
    public String getTowerName();

    public void setHex(Hexagon hex);
    public void setDmgPerAttack(int dmgPerAttack);
    public void setAttackSpeed(int beatsToWait);
    public void setTowerDrawable(TowerDrawable towerDrawable);

    /*************ACTIONS************************/
    public void rotateClockwise();
    public void attack();
    public void draw(Canvas canvas);

    public void initPaths();
    public void invalidatePaths(PointF delta); //
    public void clearWasInvalidated(); //Call this after invalidating to guarantee it only happens once


}
