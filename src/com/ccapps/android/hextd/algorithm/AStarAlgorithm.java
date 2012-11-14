package com.ccapps.android.hextd.algorithm;

import android.graphics.Point;
import com.ccapps.android.hextd.datastructure.AStarHeapNode;
import com.ccapps.android.hextd.datastructure.AStarNode;
import com.ccapps.android.hextd.datastructure.PriorityQueue;
import com.ccapps.android.hextd.datastructure.PriorityQueueImpl;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.State;

import java.util.ArrayList;
import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public class AStarAlgorithm implements CreepAlgorithm{

    Creep creep;
    Hexagon startNode;
    Hexagon goalNode;
    AStarNode src;
    AStarNode goal;

    AStarNode[][] A_STAR_NODES;

    /**
     * If the next hexagon contains a tower, re-evaluate path
     * @return
     */
    @Override
    public boolean pathNeedsEvaluation() {
        List<Hexagon> currentPath = creep.getPath();
        if (currentPath == null || currentPath.size() > 0 && currentPath.get(0).getTower() != null) {
            return true;
        }
        return false;
    }

    public int admissibleHeuristic(Hexagon h1, Hexagon h2) {
        Point p1 = h1.getGridPosition();
        Point p2 = h2.getGridPosition();
        return Math.max(Math.abs(p1.x - p2.x) , Math.abs(p1.y - p2.y));
    }

    @Override
    public List<Hexagon> buildPath(Hexagon startNode, Hexagon goalNode) {
        final HexGrid GRID = HexGrid.getInstance();
        final int ROWS = GRID.getNumVertical();
        final int COLS = GRID.getNumHorizontal();
        final Point startPos = startNode.getGridPosition();
        final Point goalPos = goalNode.getGridPosition();
        //First store heuristic values in each node
        this.startNode = startNode;
        this.goalNode = goalNode;
        A_STAR_NODES = new AStarNode[ROWS][COLS];

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                A_STAR_NODES[i][j] = new AStarNode(GRID.get(i,j), admissibleHeuristic(GRID.get(i, j), goalNode));
            }
        }

        src = A_STAR_NODES[startPos.x][startPos.y];
        goal = A_STAR_NODES[goalPos.x][goalPos.y];

        int gScore = 0;
        int fScore = gScore + src.getHeuristicCost();

        List<AStarNode> closedSet = new ArrayList<AStarNode>(); //don't need to use priority queue for closed
        PriorityQueue openSet = new PriorityQueueImpl();     //priority queue for open set
        openSet.insert(new AStarHeapNode(src));

        while (!openSet.isEmpty())
        {
            AStarNode current = (AStarNode)openSet.removeHighestPriority();
            Point currentPos = current.getHexagon().getGridPosition();

            if (current == goal)
            {
                return reconstructAStarPath();
            }
            closedSet.add(current);
            List<AStarNode> adjs = new ArrayList<AStarNode>();
            Hexagon[] nbrs = current.getHexagon().getNeighbors();
            for (int i = 0; i < nbrs.length; i++) {
                if (nbrs[i] != null && nbrs[i].getTower() == null) {
                    adjs.add(A_STAR_NODES[nbrs[i].getGridPosition().x][nbrs[i].getGridPosition().y]);
                }
            }
            for (int i = 0; i < adjs.size(); i++ )
            {
                AStarNode nbr = adjs.get(i);
                Point nbrPos = nbr.getHexagon().getGridPosition();

                if (closedSet.contains(nbr))
                    continue;

                int tentantivePathScore = current.getPathCost() + 1;

                if (!openSet.contains(nbr) || tentantivePathScore < nbr.getPathCost())
                {
                    nbr.setAStarParent(current);
                    nbr.setPathCost(tentantivePathScore);

                    if (!openSet.contains(nbr))
                        openSet.insert(new AStarHeapNode(nbr));

                }
            }
        }
        return null;
    }

    private List<Hexagon> reconstructAStarPath() {
        List<AStarNode> path = new ArrayList<AStarNode>();
        reconstructPathHelper(goal, path);
        List<Hexagon> hexPath = new ArrayList<Hexagon>();
        for (AStarNode n: path) {
            hexPath.add(n.getHexagon());
        }
        return hexPath;
    }

    private void reconstructPathHelper(AStarNode current, List<AStarNode> addToMe)
    {
        if (current.getAStarParent() == null)
        {
            return;
        }
        reconstructPathHelper(current.getAStarParent(), addToMe);
        addToMe.add(current);
    }

    @Override
    public Creep getCreep() {
        return creep;
    }

    @Override
    public void setCreep(Creep creep) {
        this.creep = creep;
    }

    // Decision analysis for creep behavior
    public void decisionAnalysis(Creep creep) {
        List<Hexagon> currentPath = creep.getPath();
        Hexagon creepHex = creep.getHex();
        Hexagon neighbors[] = creepHex.getNeighbors();

        // determine if viable candidates exist in neighbors
        List<Hexagon> candidates = new ArrayList<Hexagon>(6);
        int maxWeight = 0;
        for (Hexagon h : neighbors) {
            // skip if neighbor doesn't exist OR
            // neighbor is previously OR
            // if tower exists in path
            if (h == null || h == creep.getPrevHex() || h.getTower() != null)
                continue;
            int curWt = h.getWeight();
            if (curWt > maxWeight) {
                maxWeight = curWt;
                candidates.clear();
                candidates.add(h);
            }
            else if (curWt == maxWeight) {
                candidates.add(h);
            }
        }

        State creepState = creep.getState();
        if (creepState == State.FORAGE_FOLLOW) {
            if (currentPath == null || currentPath.size() == 0) {
                if (candidates.size() == 0) {
                    creep.setState(State.FORAGE_LEAD);
                }
                else {
                    // candidates exist
                    if (candidates.size() == 1) {   // only one candidate
                        if (currentPath == null) {
                            creep.setPath(candidates);
                        }
                        else {
                            currentPath.add(candidates.get(0));
                        }
                    }
                }
            }
        }
        else if (creepState == State.FORAGE_LEAD) {
            if (currentPath == null || currentPath.size() == 0) {
                // no path exists, check if goal reached
                if (creep.getHex() == creep.getGoalHex()) {
                    // goal reached, return to hive
                    creep.setState(State.RETURN_LEAD);
                }
                else {
                    // recalculate Astar to goal
                    creep.setPath(this.buildPath(creep.getHex(), creep.getGoalHex()));
                }
            }
            else {
                // path exists
                if (currentPath.get(0).getTower() != null) {
                    // tower exists at node. if it's goal hex, has food and now return
                    if (currentPath.get(0) == this.goalNode) {
                        creep.setState(State.RETURN_LEAD);
                        creep.setPath(null);
                    }
                }
            }
        }

    }
}
