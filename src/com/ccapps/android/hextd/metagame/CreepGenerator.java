package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public interface CreepGenerator {
    public void tick();

    public int getSpeed();
    public void setSpeed(int speed);

    public List<Hexagon> getSourceHexes();
    public void setSourceHexes(List<Hexagon> sourceHexes);

    public void addCreepToQueue(Creep c);

    public void setCreepClass(Class <? extends Creep> creepClass);
    public void setCreepAlgorithm(Class <? extends CreepAlgorithm> creepAlgorithm);
}
//CLC: Original Code End
