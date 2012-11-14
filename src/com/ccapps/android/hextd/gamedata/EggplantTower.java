package com.ccapps.android.hextd.gamedata;

import android.graphics.Point;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

import java.util.ArrayList;
import java.util.Collections;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class EggplantTower extends BasicTower{
    public EggplantTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.EGGPLANT);
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        for (int i = 0; i < 6; i++) {
            addSafe(hex.getNeighbors()[i]);
        }

    }

    @Override
    public int getDmgPerAttack() {
        return random.nextInt(11) + 10;
    }

    @Override
    public String getDamageString() {
        return "10 - 20 damage";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTowerName() {
        return "Jumping Eggplant";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rotateClockwise() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
//CLC: Original Code End
