package com.ccapps.android.hextd.datastructure;

import com.ccapps.android.hextd.draw.Hexagon;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AStarNode {
    private int pathCost;
    private int heuristicCost;
    private AStarNode aStarParent;
    private Hexagon hexagon;

    public AStarNode(Hexagon hexagon, int heuristicCost) {
        this.hexagon = hexagon;
        this.pathCost = Integer.MAX_VALUE / 2;
        this.heuristicCost = heuristicCost;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getHeuristicCost() {
        return this.heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getTotalCost() {
        return heuristicCost + pathCost;
    }

    public AStarNode getAStarParent() {
        return this.aStarParent;
    }

    public void setAStarParent(AStarNode p) {
        this.aStarParent = p;
    }

    public Hexagon getHexagon() {
        return hexagon;
    }

    public void setHexagon(Hexagon h) {
        this.hexagon = h;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof AStarNode)) {
            return false;
        }
        return ((AStarNode)o).hexagon.equals(this.hexagon);
    }


}
