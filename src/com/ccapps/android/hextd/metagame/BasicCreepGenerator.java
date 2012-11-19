package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.algorithm.AStarAlgorithm;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.algorithm.RandomWalkAlgorithm;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.AntCreep;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.CreepUtils;

import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class BasicCreepGenerator implements CreepGenerator{
    protected Class<? extends Creep> creepClass;
    protected Class<? extends CreepAlgorithm> creepAlgorithm;
    protected int speed = 24;
    protected int tick = 0;
    protected List<Hexagon> sourceHexes;
    protected Hexagon goalHex;

    public BasicCreepGenerator(List<Hexagon> sourceHexes, Hexagon goalHex) {
        this.sourceHexes = sourceHexes;
        this.goalHex = goalHex;
        this.creepClass = AntCreep.class;
        this.creepAlgorithm = AStarAlgorithm.class;
        for (Hexagon h: sourceHexes){
            h.setState(Hexagon.STATE.SOURCE);
        }
    }

    @Override
    public void tick() {
        ++tick;
        if (tick % speed == 0) {
           for (Hexagon h: sourceHexes) {
               CreepUtils.addCreep(creepClass, h, goalHex, creepAlgorithm);
           }
        }
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setCreepClass(Class<? extends Creep> creepClass) {
        this.creepClass = creepClass;
    }

    @Override
    public void setCreepAlgorithm(Class<? extends CreepAlgorithm> creepAlgorithm) {
        this.creepAlgorithm = creepAlgorithm;
    }

    @Override
    public List<Hexagon> getSourceHexes() {
        return sourceHexes;
    }

    @Override
    public void setSourceHexes(List<Hexagon> sourceHexes) {
        this.sourceHexes = sourceHexes;
    }

    @Override
    public void addCreepToQueue(Creep c) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
//CLC: Original Code End
