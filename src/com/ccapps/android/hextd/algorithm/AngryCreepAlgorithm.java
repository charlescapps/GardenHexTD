package com.ccapps.android.hextd.algorithm;

import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.AngryAntCreep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ccapps.android.hextd.gamedata.AngryAntCreep.STATE;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin

public class AngryCreepAlgorithm extends AStarAlgorithm{

    @Override
    public List<Hexagon> buildPath(Hexagon startNode, Hexagon goalNode) {
        AngryAntCreep angryCreep = (AngryAntCreep)creep;
        STATE state = angryCreep.getState();
        if (state == STATE.NEUTRAL || state == STATE.ANGRY) {
            return super.buildPath(startNode, goalNode);
        }
        List<Hexagon> path = new ArrayList<Hexagon>();
        List<Hexagon> nbrs = new ArrayList<Hexagon>();
        for (Hexagon h: startNode.getNeighbors()) {
            if (h != null && h.getTower() == null) {
                nbrs.add(h);
            }
        }

        int i = new Random().nextInt(nbrs.size());
        path.add(nbrs.get(i));
        return path;

    }

    @Override
    public boolean pathNeedsEvaluation() {
        List<Hexagon> currentPath = creep.getPath();
        if (currentPath == null || currentPath.size() <= 0 || currentPath.size() > 0 && currentPath.get(0).getTower() != null) {
            return true;
        }
        AngryAntCreep angryCreep = (AngryAntCreep)creep;

        if (angryCreep.getState() == STATE.EVADE) {
            return true;
        }

        return false;
    }
}
//CLC: Original Code End

