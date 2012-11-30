package com.ccapps.android.hextd.gamedata;

import android.graphics.Point;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.algorithm.ScentAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
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

            }
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        Point pos = hex.getGridPosition();
        ScentAlgorithm.scents[pos.x][pos.y] = Math.max(-100, ScentAlgorithm.scents[pos.x][pos.y]-30);
        for (Hexagon h: hex.getNeighbors()) {
            if (h != null) {
                pos = h.getGridPosition();
                ScentAlgorithm.scents[pos.x][pos.y] = Math.max(-100, ScentAlgorithm.scents[pos.x][pos.y]-10);

            }
        }
    }

    @Override
    public boolean equals(Object o) {
        return this==o;
    }
}
//CLC: Original Code End
