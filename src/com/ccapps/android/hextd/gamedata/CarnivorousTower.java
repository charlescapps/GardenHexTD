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
public class CarnivorousTower extends BasicTower{
    public CarnivorousTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.CARNIVOROUS);
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        Point pos = hex.getGridPosition();

        addSafe(GRID.get(pos.x - 1, pos.y));
        addSafe(GRID.get(pos.x - 2, pos.y));


    }

    @Override
    public void reEvaluateAttackHexes(int oldDir, int newDir) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
