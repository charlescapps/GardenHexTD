package com.ccapps.android.hextd.gamedata.terrain;

import android.graphics.Canvas;
import android.graphics.PointF;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.StaticData;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/27/12
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BarrierTower extends BasicTower implements DummyTower{

    public BarrierTower(Hexagon hex) {
        super(hex);
        this.towerDrawable = new TowerDrawable(this, StaticData.TREE);
        this.attackHexes = new ArrayList<Hexagon>();
    }

    @Override
    public int getDmgPerAttack() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Hexagon> getAttackHexes() {
        return attackHexes;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAttackSpeed() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDamageString() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTowerName() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rotateClockwise() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
