package com.ccapps.android.hextd.algorithm;

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
public interface CreepAlgorithm {
    public boolean pathNeedsEvaluation();
    public List<Hexagon> buildPath(Hexagon src, Hexagon goal);

    public Creep getCreep();
    public void setCreep(Creep creep);

}
//CLC: Original Code End
