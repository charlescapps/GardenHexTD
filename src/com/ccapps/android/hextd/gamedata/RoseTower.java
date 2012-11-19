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
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class RoseTower extends BasicTower{
    public RoseTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.ROSE);
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        this.direction = 0;
        HexGrid GRID = HexGrid.getInstance();

        Point pos = hex.getGridPosition();
        addSafe(GRID.get(pos.x-1, pos.y));

        if (pos.y % 2 == 0) {
            addSafe(GRID.get(pos.x+1, pos.y+1));
            addSafe(GRID.get(pos.x+1, pos.y-1));
        }
        else {
            addSafe(GRID.get(pos.x, pos.y+1));
            addSafe(GRID.get(pos.x, pos.y-1));
        }

    }

    @Override
    public String getDamageString() {
        return "10 - 100 damage";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTowerName() {
        return "Brambles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rotateClockwise() {
        ++direction;
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();
        Point pos = hex.getGridPosition();

        switch(direction % 2) {
            case 0:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x-1, pos.y));
                    addSafe(GRID.get(pos.x+1, pos.y+1));
                    addSafe(GRID.get(pos.x+1, pos.y-1));
                }
                else {
                    addSafe(GRID.get(pos.x-1, pos.y));
                    addSafe(GRID.get(pos.x, pos.y+1));
                    addSafe(GRID.get(pos.x, pos.y-1));
                }
                break;

            case 1:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x, pos.y+1));
                    addSafe(GRID.get(pos.x, pos.y-1));
                    addSafe(GRID.get(pos.x + 1,pos.y));
                }
                else {
                    addSafe(GRID.get(pos.x - 1, pos.y+1));
                    addSafe(GRID.get(pos.x - 1, pos.y-1));
                    addSafe(GRID.get(pos.x + 1, pos.y));
                }
                break;
        }
        initAttackHexPaths();
    }

    @Override
    public int getDmgPerAttack() {
        return random.nextInt(91) + 10;
    }
}
//CLC: Original Code End
