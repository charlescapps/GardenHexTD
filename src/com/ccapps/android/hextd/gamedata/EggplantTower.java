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
}
