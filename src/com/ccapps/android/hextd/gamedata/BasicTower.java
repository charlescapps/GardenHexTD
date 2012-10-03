package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.Point;
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

    public BasicTower(Hexagon hex) {
        this.dmgPerAttack = 20;
        this.hex = hex;
        Point pos = hex.getGridPosition();
        this.attackHexes = new Hexagon[1];
        this.isAttacking = false;
        this.beatsToWait = 2;
        HexGrid grid = HexGrid.getInstance();
        int numVertical = grid.getNumVertical();

        if (pos.x + 1 < numVertical) {
            attackHexes[0] = grid.get(pos.x + 1, pos.y);
        } else {
            attackHexes[0] = null;
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
            if (relativeBeat % beatsToWait == 0) {
                h.attacked(dmgPerAttack, true);
            } else {
                h.attacked(0, false);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        this.towerDrawable.draw(canvas);
        if (this.isAttacking) {
            for (Hexagon h: this.getAttackHexes()) {
                h.draw(canvas);
            }
        }
    }
}
