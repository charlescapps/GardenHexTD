package com.ccapps.android.hextd.gamedata;

import com.ccapps.android.hextd.draw.Hexagon;

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

    public void settHex(Hexagon hex);
    public void setDmgPerAttack(int dmgPerAttack);
    public void setBeatsToWait(int beatsToWait);

    /*************ACTIONS************************/
    public void rotateClockwise(int numHexes);
    public void attack();

}
