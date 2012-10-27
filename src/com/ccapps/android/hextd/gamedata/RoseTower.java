package com.ccapps.android.hextd.gamedata;

import android.graphics.Point;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/13/12
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoseTower extends BasicTower{
    public RoseTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.ROSE);
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getDmgPerAttack() {
        return random.nextInt(91) + 10;
    }
}
