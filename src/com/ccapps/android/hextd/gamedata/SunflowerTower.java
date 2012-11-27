package com.ccapps.android.hextd.gamedata;

import android.graphics.Point;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerAnimatedAttackDrawable;
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
public class SunflowerTower extends BasicTower{

    public SunflowerTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerAnimatedAttackDrawable(this, StaticData.SUNFLOWER, StaticData.SUNSHINE_ANIMATION);
        this.attackSpeed = 4;
        this.direction = 0;

        if (hex.getGridPosition().y % 2 == 0) {
            initModZeroAttackHexes();
        }
        else {
            initModOneAttackHexes();
        }
    }

    @Override
    public void rotateClockwise() {
        ++direction;
        if (hex.getGridPosition().y % 2 == 0) {
            initModZeroAttackHexes();
        }
        else {
            initModOneAttackHexes();
        }

        initAttackHexPaths();
    }

    private void initModZeroAttackHexes() {
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        Point pos = hex.getGridPosition();

        switch(direction % 6) {
            case 0:
                addSafe(GRID.get(pos.x, pos.y -1));
                addSafe(GRID.get(pos.x-1, pos.y-2));

                addSafe(GRID.get(pos.x-1, pos.y));
                addSafe(GRID.get(pos.x-2, pos.y));

                addSafe(GRID.get(pos.x, pos.y+1));
                addSafe(GRID.get(pos.x-1, pos.y+2));

                break;
            case 1:
                addSafe(GRID.get(pos.x-1, pos.y));
                addSafe(GRID.get(pos.x-2, pos.y));

                addSafe(GRID.get(pos.x, pos.y+1));
                addSafe(GRID.get(pos.x-1, pos.y+2));

                addSafe(GRID.get(pos.x+1, pos.y+1));
                addSafe(GRID.get(pos.x+1, pos.y+2));

                break;
            case 2:

                addSafe(GRID.get(pos.x, pos.y+1));
                addSafe(GRID.get(pos.x-1, pos.y+2));

                addSafe(GRID.get(pos.x+1, pos.y+1));
                addSafe(GRID.get(pos.x+1, pos.y+2));

                addSafe(GRID.get(pos.x+1, pos.y));
                addSafe(GRID.get(pos.x+2, pos.y));

                break;

            case 3:
                addSafe(GRID.get(pos.x+1, pos.y+1));
                addSafe(GRID.get(pos.x+1, pos.y+2));

                addSafe(GRID.get(pos.x+1, pos.y));
                addSafe(GRID.get(pos.x+2, pos.y));

                addSafe(GRID.get(pos.x+1, pos.y-1));
                addSafe(GRID.get(pos.x+1, pos.y-2));
                break;

            case 4:

                addSafe(GRID.get(pos.x+1, pos.y));
                addSafe(GRID.get(pos.x+2, pos.y));

                addSafe(GRID.get(pos.x+1, pos.y-1));
                addSafe(GRID.get(pos.x+1, pos.y-2));

                addSafe(GRID.get(pos.x, pos.y-1));
                addSafe(GRID.get(pos.x-1, pos.y-2));


                break;

            case 5:

                addSafe(GRID.get(pos.x+1, pos.y-1));
                addSafe(GRID.get(pos.x+1, pos.y-2));

                addSafe(GRID.get(pos.x, pos.y-1));
                addSafe(GRID.get(pos.x-1, pos.y-2));

                addSafe(GRID.get(pos.x-1, pos.y));
                addSafe(GRID.get(pos.x-2, pos.y));
                break;
        }
    }

    private void initModOneAttackHexes() {
        this.attackHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        Point pos = hex.getGridPosition();

        switch (direction % 6) {

            case 0:
                addSafe(GRID.get(pos.x-1, pos.y -1));
                addSafe(GRID.get(pos.x - 1, pos.y-2));

                addSafe(GRID.get(pos.x-1 , pos.y ));
                addSafe(GRID.get(pos.x-2 , pos.y ));

                addSafe(GRID.get(pos.x - 1, pos.y+1));
                addSafe(GRID.get(pos.x - 1, pos.y+2));
                break;
            case 1:
                addSafe(GRID.get(pos.x-1 , pos.y ));
                addSafe(GRID.get(pos.x-2 , pos.y ));

                addSafe(GRID.get(pos.x - 1, pos.y+1));
                addSafe(GRID.get(pos.x - 1, pos.y+2));

                addSafe(GRID.get(pos.x , pos.y+1));
                addSafe(GRID.get(pos.x + 1, pos.y+2));

                break;

            case 2:

                addSafe(GRID.get(pos.x - 1, pos.y+1));
                addSafe(GRID.get(pos.x - 1, pos.y+2));

                addSafe(GRID.get(pos.x , pos.y+1));
                addSafe(GRID.get(pos.x + 1, pos.y+2));

                addSafe(GRID.get(pos.x +1, pos.y));
                addSafe(GRID.get(pos.x +2, pos.y));

                break;

            case 3:

                addSafe(GRID.get(pos.x , pos.y+1));
                addSafe(GRID.get(pos.x + 1, pos.y+2));

                addSafe(GRID.get(pos.x +1, pos.y));
                addSafe(GRID.get(pos.x +2, pos.y));

                addSafe(GRID.get(pos.x +1, pos.y -2));
                addSafe(GRID.get(pos.x, pos.y-1));

                break;

            case 4:

                addSafe(GRID.get(pos.x +1, pos.y));
                addSafe(GRID.get(pos.x +2, pos.y));

                addSafe(GRID.get(pos.x +1, pos.y -2));
                addSafe(GRID.get(pos.x, pos.y-1));

                addSafe(GRID.get(pos.x-1, pos.y -1));
                addSafe(GRID.get(pos.x - 1, pos.y-2));

                break;

            case 5:

                addSafe(GRID.get(pos.x +1, pos.y -2));
                addSafe(GRID.get(pos.x, pos.y-1));

                addSafe(GRID.get(pos.x-1, pos.y -1));
                addSafe(GRID.get(pos.x - 1, pos.y-2));

                addSafe(GRID.get(pos.x-1 , pos.y ));
                addSafe(GRID.get(pos.x-2 , pos.y ));

                break;

        }
    }



    @Override
    public int getDmgPerAttack() {
        return 10;
    }

    @Override
    public String getDamageString() {
        return "10 - 10 damage";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTowerName() {
        return "Sunflower";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
//CLC: Original Code End
