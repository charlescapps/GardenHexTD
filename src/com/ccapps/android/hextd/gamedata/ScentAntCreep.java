package com.ccapps.android.hextd.gamedata;

import android.graphics.Point;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.algorithm.ScentAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 11/18/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScentAntCreep extends AntCreep{
    private boolean wasDead;
    public ScentAntCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm) {
        super(hex, goalHex, algorithm);
        wasDead = false;

    }

    @Override
    public void move() {
        super.move();
        if (tick % speed == 0) {
            if (hitpoints > 0 && forageState != FORAGE_STATE.BACK_TO_HIVE) {
                Point pos = hex.getGridPosition();
                ScentAlgorithm.scents[pos.x][pos.y] = Math.min(100, ScentAlgorithm.scents[pos.x][pos.y] + 1);
                if (hex == goalHex) {
                    if (forageState == FORAGE_STATE.FORAGE) {
                        goalHex = sourceHex;
                        sourceHex = hex;
                        path = null;
                        forageState = FORAGE_STATE.RETURN;
                    }
                    else {
                         forageState = FORAGE_STATE.BACK_TO_HIVE;
                          HexGrid.getInstance().removeCreep(this);
                         hex.removeCreep(this);
                    }
                }
            }
            if (hitpoints <= 0 && !wasDead && tick % speed == 0 ) {
                wasDead = true;
                HexGrid GRID = HexGrid.getInstance();
                Point pos = hex.getGridPosition();
                for (int i = pos.x-1; i <= pos.x + 1; i++) {
                    for (int j = pos.y - 1; j <= pos.y + 1; j++ ) {
                        if (i >= 0 && i < GRID.getNumVertical() && j >= 0 && j < GRID.getNumHorizontal()) {
                            //Subtract 0.5f from the scents on surrounding hexes when an ant dies
                            ScentAlgorithm.scents[i][j] = Math.max(-100, ScentAlgorithm.scents[i][j] - 20);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        return this==o;
    }
}
