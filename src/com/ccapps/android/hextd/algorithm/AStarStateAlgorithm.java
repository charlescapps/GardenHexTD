package com.ccapps.android.hextd.algorithm;

import android.graphics.Point;
import com.ccapps.android.hextd.datastructure.AStarHeapNode;
import com.ccapps.android.hextd.datastructure.AStarNode;
import com.ccapps.android.hextd.datastructure.PriorityQueue;
import com.ccapps.android.hextd.datastructure.PriorityQueueImpl;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.State;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 11/4/12
 * Time: 8:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AStarStateAlgorithm implements CreepAlgorithm {

    @Override
    public List<Hexagon> buildPath(Hexagon startNode, Hexagon goalNode) {
        if (this.creep.getState() == State.FORAGE_FOLLOW) {
            Hexagon hex = this.creep.getHex();
            Hexagon neighbors[] = hex.getNeighbors();
            List<Hexagon> candidates = new ArrayList<Hexagon>(6);
            int maxWeight = -100;

            for (Hexagon h : neighbors) {
                if (h != null) {
                    if (h == creep.getPrevHex())
                        continue;
                    int weight = h.getWeight();
                    if (weight > maxWeight) {
                        candidates.clear();
                        maxWeight = h.getWeight();
                        candidates.add(h);
                    }
                    else if (weight == maxWeight) {
                        candidates.add(h);
                    }
                }
            }

            // set to lead
            if (maxWeight <= 0) {
                creep.setState(State.FORAGE_LEAD);
            }
            else { // weight > 0
                if (candidates.size() == 1) {
                    creep.setPath(candidates);
                }
            }
        }
        else if (this.creep.getState() == State.FORAGE_LEAD)
            return super.buildPath(startNode, goalNode);
        else
            return null;
    }

    @Override
    public void setCreep(Creep creep) {
        this.creep = creep;
    }

}
