package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.PointF;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

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
    public Hexagon[] getAttackHexes(); //Read-only, not set directly
    public int getBeatsToWait();
    public TowerDrawable getTowerDrawable();

    public void setHex(Hexagon hex);
    public void setDmgPerAttack(int dmgPerAttack);
    public void setBeatsToWait(int beatsToWait);
    public void setTowerDrawable(TowerDrawable towerDrawable);

    /*************ACTIONS************************/
    public void rotateClockwise(int numHexes);
    public void attack();
    public void draw(Canvas canvas);

    public void initPaths();
    public void invalidatePaths(PointF delta); //
    public void clearWasInvalidated(); //Call this after invalidating to guarantee it only happens once


}
