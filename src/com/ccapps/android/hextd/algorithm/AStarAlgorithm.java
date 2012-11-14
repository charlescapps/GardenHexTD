package com.ccapps.android.hextd.algorithm;

import android.graphics.Point;
import com.ccapps.android.hextd.datastructure.AStarHeapNode;
import com.ccapps.android.hextd.datastructure.AStarNode;
import com.ccapps.android.hextd.datastructure.PriorityQueue;
import com.ccapps.android.hextd.datastructure.PriorityQueueImpl;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/21/12
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AStarAlgorithm implements CreepAlgorithm {

    protected Creep creep;
    protected Hexagon startNode;
    protected Hexagon goalNode;
    protected AStarNode src;
    protected AStarNode goal;

    protected AStarNode[][] A_STAR_NODES;

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
}
