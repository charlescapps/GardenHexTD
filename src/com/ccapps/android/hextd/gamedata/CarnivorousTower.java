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
public class CarnivorousTower extends BasicTower{

    public CarnivorousTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.CARNIVOROUS);
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        this.direction = 0;
        HexGrid GRID = HexGrid.getInstance();

        Point pos = hex.getGridPosition();

        addSafe(GRID.get(pos.x - 1, pos.y));
        addSafe(GRID.get(pos.x - 2, pos.y));


    }

    @Override
    public String getDamageString() {
        return "20 - 30 damage";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTowerName() {
        return "Carnivorous Plant";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rotateClockwise() {
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());

        ++direction;
        HexGrid GRID = HexGrid.getInstance();
        Point pos = hex.getGridPosition();

        switch (direction % 6) {
            case 0:
                addSafe(GRID.get(pos.x - 1, pos.y));
                addSafe(GRID.get(pos.x - 2, pos.y));
                break;
            case 1:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x, pos.y + 1));
                    addSafe(GRID.get(pos.x - 1, pos.y + 2));
                } else {
                    addSafe(GRID.get(pos.x - 1, pos.y + 1));
                    addSafe(GRID.get(pos.x - 1, pos.y + 2));
                }
                break;
            case 2:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x + 1, pos.y + 1));
                    addSafe(GRID.get(pos.x + 1, pos.y + 2));
                } else {
                    addSafe(GRID.get(pos.x , pos.y + 1));
                    addSafe(GRID.get(pos.x + 1, pos.y + 2));
                }
                break;
            case 3:
                addSafe(GRID.get(pos.x + 1, pos.y ));
                addSafe(GRID.get(pos.x + 2, pos.y ));
                break;
            case 4:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x + 1, pos.y - 1));
                    addSafe(GRID.get(pos.x + 1, pos.y - 2));
                } else {
                    addSafe(GRID.get(pos.x , pos.y - 1));
                    addSafe(GRID.get(pos.x + 1, pos.y - 2));
                }
                break;
            case 5:
                if (pos.y % 2 == 0) {
                    addSafe(GRID.get(pos.x, pos.y - 1));
                    addSafe(GRID.get(pos.x - 1, pos.y - 2));
                } else {
                    addSafe(GRID.get(pos.x - 1 , pos.y - 1));
                    addSafe(GRID.get(pos.x - 1, pos.y - 2));
                }
                break;
        }

        initAttackHexPaths();

    }

    @Override
    public int getDmgPerAttack() {
        return random.nextInt(11) + 20;
    }
}
