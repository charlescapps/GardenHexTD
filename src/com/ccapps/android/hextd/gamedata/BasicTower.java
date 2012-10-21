package com.ccapps.android.hextd.gamedata;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/30/12
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BasicTower implements Tower {

    protected Hexagon hex;
    protected int dmgPerAttack;
    protected List<Hexagon> attackHexes;
    protected int beatsToWait;
    protected int relativeBeat;
    protected TowerDrawable towerDrawable;
    private boolean isAttacking;
    protected int direction;

    public BasicTower(Hexagon hex) {
        this.dmgPerAttack = 20;
        this.hex = hex;
        this.isAttacking = false;
        this.beatsToWait = 2;
        this.direction = 0;
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
    public List<Hexagon> getAttackHexes() {
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
    public int getDirection() {
        return direction;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDirection(int direction) {
        reEvaluateAttackHexes(direction, this.direction);

        this.direction = direction;
    }

    @Override
    public void setTowerDrawable(TowerDrawable towerDrawable) {
        this.towerDrawable = towerDrawable;
    }

    @Override
    public void rotateClockwise(int num) {
        reEvaluateAttackHexes(this.direction, this.direction+num);
        direction += num;
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

    protected void addSafe(Hexagon h) {
        if (h != null) {
            attackHexes.add(h);
        }
    }

    abstract public void reEvaluateAttackHexes(int oldDir, int newDir); //Called after setting the direction


}
