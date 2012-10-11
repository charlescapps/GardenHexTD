package com.ccapps.android.hextd.gamedata;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/30/12
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicTower implements Tower {

    private Hexagon hex;
    private int dmgPerAttack;
    private Hexagon[] attackHexes;
    private int beatsToWait;
    private int relativeBeat;
    private TowerDrawable towerDrawable;
    private boolean isAttacking;

    public BasicTower() {
       //Call initTower after this.

    }

    public void initTower(Hexagon hex) {

        this.dmgPerAttack = 20;
        this.hex = hex;
        this.attackHexes = new Hexagon[4];
        this.isAttacking = false;
        this.beatsToWait = 2;

        Point pos = hex.getGridPosition();
        HexGrid grid = HexGrid.getInstance();
        int numVertical = grid.getNumVertical();
        int numHorizontal = grid.getNumHorizontal();

        if (pos.x + 1 < numVertical) {
            attackHexes[0] = grid.get(pos.x + 1, pos.y);
        } else {
            attackHexes[0] = null;
        }

        if (pos.x - 1 >= 0) {
            attackHexes[1] = grid.get(pos.x - 1, pos.y);
        } else {
            attackHexes[1] = null;
        }

        if (pos.y + 1 < numHorizontal) {
            attackHexes[2] = grid.get(pos.x, pos.y+1);
        } else {
            attackHexes[2] = null;
        }

        if (pos.y - 1 >= 0) {
            attackHexes[3] = grid.get(pos.x, pos.y-1);
        } else {
            attackHexes[3] = null;
        }

        this.towerDrawable = new TowerDrawable(this, StaticData.BASIC_TOWER_IMAGE);

    }


    public BasicTower(Hexagon hex) {
        initTower(hex);
    }

    @Override
    public void initPaths() {
        this.hex.initPath();
        for (Hexagon h: this.attackHexes) {
            if (h != null) {
                h.initPath();
            }
        }
    }

    public void invalidatePaths(PointF delta) {

        for (Hexagon h: attackHexes) {
            if (h != null) {
                h.invalidatePath(delta);
            }
        }

    }

    public void clearWasInvalidated() {
        for (Hexagon h: attackHexes) {
            if (h != null) {
                h.clearWasInvalidated();
            }
        }
    }


    @Override
    public Hexagon getHex() {
        return hex;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHex(Hexagon hex) {
        this.hex = hex;
    }

    @Override
    public int getDmgPerAttack() {
        return dmgPerAttack;
    }

    @Override
    public void setDmgPerAttack(int dmgPerAttack) {
        this.dmgPerAttack = dmgPerAttack;
    }

    @Override
    public Hexagon[] getAttackHexes() {
        return attackHexes;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getBeatsToWait() {
        return beatsToWait;
    }

    @Override
    public void setBeatsToWait(int beatsToWait) {
        this.beatsToWait = beatsToWait;
    }

    @Override
    public TowerDrawable getTowerDrawable() {
        return this.towerDrawable;
    }

    @Override
    public void setTowerDrawable(TowerDrawable towerDrawable) {
        this.towerDrawable = towerDrawable;
    }

    @Override
    public void rotateClockwise(int numHexes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void attack() {
        this.isAttacking = true;
        ++relativeBeat;
        for (Hexagon h: attackHexes) {
            if (h != null) {
                if (relativeBeat % beatsToWait == 0) {
                    h.attacked(dmgPerAttack, true);
                } else {
                    h.attacked(0, false);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        this.towerDrawable.draw(canvas);
        if (this.isAttacking) {
            for (Hexagon h: this.getAttackHexes()) {
                if (h != null) {
                    h.draw(canvas);
                }
            }
        }
    }
}
