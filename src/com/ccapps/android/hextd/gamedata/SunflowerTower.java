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
public class SunflowerTower extends BasicTower{
    public SunflowerTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.SUNFLOWER);
        this.dmgPerAttack = 10;

        initModZeroAttackHexes();
    }

    @Override
    public void reEvaluateAttackHexes(int oldDir, int newDir) {
        if (oldDir % 2 == newDir % 2) {
            return;
        }
        if (oldDir % 2 == 0) {
            initModOneAttackHexes();
        }
        else {
            initModZeroAttackHexes();
        }
    }

    private void initModZeroAttackHexes() {
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        for (Hexagon h: hex.getNeighbors()) {
            addSafe(h);
        }

        Point pos = hex.getGridPosition();

        addSafe(GRID.get(pos.x - 2, pos.y));
        addSafe(GRID.get(pos.x + 2, pos.y));

        addSafe(GRID.get(pos.x - 1, pos.y-2));
        addSafe(GRID.get(pos.x - 1, pos.y+2));
        addSafe(GRID.get(pos.x + 1, pos.y-2));
        addSafe(GRID.get(pos.x + 1, pos.y+2));

    }

    private void initModOneAttackHexes() {
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        for (Hexagon h: hex.getNeighbors()) {
            addSafe(h);
        }

        Point pos = hex.getGridPosition();

        addSafe(GRID.get(pos.x , pos.y - 2));
        addSafe(GRID.get(pos.x, pos.y + 2));

        addSafe(GRID.get(pos.x - 2, pos.y-1));
        addSafe(GRID.get(pos.x - 2, pos.y+1));
        addSafe(GRID.get(pos.x + 2, pos.y-1));
        addSafe(GRID.get(pos.x + 2, pos.y+1));

    }

}
