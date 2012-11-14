package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.PointF;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;

import java.util.List;
import java.util.Random;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public abstract class BasicTower implements Tower {

    protected Hexagon hex;
    protected int dmgPerAttack;
    protected List<Hexagon> attackHexes;
    protected int attackSpeed;
    protected int relativeBeat;
    protected TowerDrawable towerDrawable;
    private boolean isAttacking;
    protected int direction;
    protected Random random;

    public BasicTower(Hexagon hex) {
        this.dmgPerAttack = 20;
        this.hex = hex;
        this.isAttacking = false;
        this.attackSpeed = 4;
        this.direction = 0;
        this.random = new Random();
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
    public int getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
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
        this.direction = direction;
    }

    @Override
    public void setTowerDrawable(TowerDrawable towerDrawable) {
        this.towerDrawable = towerDrawable;
    }

    @Override
    public void attack() {
        towerDrawable.incrementAttackAnimation();
        this.isAttacking = true;
        ++relativeBeat;
        for (Hexagon h: attackHexes) {
            if (h != null) {
                if (relativeBeat % attackSpeed == 0) {
                    h.attacked(dmgPerAttack);
                } else {
                    h.attacked(0);
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

    protected void initAttackHexPaths() {
        for (Hexagon h: getAttackHexes()) {
            h.initPath();
        }
    }

}
