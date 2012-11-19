package com.ccapps.android.hextd.datastructure;

import com.ccapps.android.hextd.draw.Hexagon;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
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
//CLC: Original Code End
