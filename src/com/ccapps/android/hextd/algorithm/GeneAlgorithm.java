package com.ccapps.android.hextd.algorithm;

import android.graphics.Point;
import com.ccapps.android.hextd.datastructure.AStarHeapNode;
import com.ccapps.android.hextd.datastructure.AStarNode;
import com.ccapps.android.hextd.datastructure.PriorityQueue;
import com.ccapps.android.hextd.datastructure.PriorityQueueImpl;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.Gene;
import com.ccapps.android.hextd.gamedata.State;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.XMLFormatter;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

// JL: Original Code Begin
/*  Gene Algorithm is an adaptation of the Creep A* Algorithm
    Implementation of pathing involving genetic attributes of creep
 */
public class GeneAlgorithm implements CreepAlgorithm{

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
    // JL: Original Code Halt

    //CLC: Adapted Code Wikipedia article on A*
    //CLC: Adapted Code Begin
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
    // CLC: Adapted Code End

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
    // CLC: Original Code End

    // JL: Original Code Resume
    // Decision analysis for creep behavior
    public void decisionAnalysis(Creep creep) {
        List<Hexagon> currentPath = creep.getPath();
        Hexagon creepHex = creep.getHex();
        Gene genome  = creep.getAttr();
        //Hexagon neighbors[] = creepHex.getNeighbors();

        // expired stamina or goal reached, return to source
        if (creep.getState() == State.FORAGE_FOLLOW ||
                creep.getState() == State.FORAGE_LEAD) {
            // check if goal has been reached
            if (creepHex == creep.getGoalHex()) {
                creep.setGoalMet(true);
                // return home, sourceHex
                creep.setGoalHex(creep.getSourceHex());
                creep.setPath(this.buildPath(creep.getHex(), creep.getGoalHex()));
            }

            // check if stamina has run out
            int stam = genome.getStamina() + this.admissibleHeuristic(creep.getSourceHex(), creep.getGoalHex());
            if (creep.getStepCount() > stam && creep.getGoalMet() == false) {
                // return home, sourceHex
                creep.setGoalHex(creep.getSourceHex());
                creep.setPath(this.buildPath(creep.getHex(), creep.getGoalHex()));
            }
            creep.setState(State.RETURN_LEAD);
        }

        // random step chance from genome
        Hexagon rand = this.takeRandomStep(creep);
        if (rand != null) {
            List<Hexagon> newPath = new ArrayList<Hexagon>();
            newPath.add(rand);
            creep.setPath(newPath);
            return;
        }

        State creepState = creep.getState();

        // arrived at goal
        if (creepHex == goalNode) {
            // set path to home colony
            creep.setPath(null);
        }

        // path exists
        if (currentPath != null && currentPath.size() > 0) {
            // check if next path traversible
            if (this.traversable(currentPath.get(0))) {
                if (this.survivalEstimate(creep)) {
                    // traversable and allowable by survival heuristic
                    return;
                }
                else {
                    // fails survival estimate, take safestep
                    List<Hexagon> newPath = new ArrayList<Hexagon>();
                    newPath.add(this.safeStep(creep));
                    creep.setPath(newPath);
                }
            }
            else {
                // next step non-traversable
                List<Hexagon> newPath = new ArrayList<Hexagon>();
                //Hexagon safeHex = this.safeStep(creep);
                newPath.add(this.estimateStep(creep));
                creep.setPath(newPath);
            }
        }
        else {
            // path doesn't exist, construct new path
            List<Hexagon> newPath = this.buildPath(creepHex, creep.getGoalHex());
            creep.setPath(newPath);
            // validate path
            if (this.traversable(newPath.get(0))) {
                // check for safety attr
                if (this.survivalEstimate(creep)) {
                    return;
                }
                else {
                    // surv est fails
                    newPath.clear();
                    newPath.add(this.safeStep(creep));
                }
            }
            else {
                // new Astar path is untraversable
                newPath.clear();
                newPath.add(this.estimateStep(creep));
                creep.setPath(newPath);
            }

        }

    }

    // step to take when in evasive/survival mode
    private Hexagon safeStep(Creep creep) {
        Hexagon current = creep.getHex();
        Hexagon neighbors[] = current.getNeighbors();

        Hexagon nextGuess = this.estimateStep(creep);
        if (nextGuess.getMyState() != Hexagon.STATE.ATTACKED) {
            return nextGuess;
        }
        else {
            // choose best guess neighbor not under attack
            List<Hexagon> notAttacked = new ArrayList<Hexagon>();
            for (Hexagon h : neighbors) {
                if (h == null)
                    continue;
                if (h.getMyState() != Hexagon.STATE.ATTACKED &&
                        h != creep.getPrevHex()) {
                    notAttacked.add(h);
                }
            }
            // choose best not-attacked hex
            switch(notAttacked.size()) {
                case 0:
                    return null;
                case 1:
                    return notAttacked.get(0);
                default:
                    Hexagon closestSafe = null,
                            goalHex = creep.getGoalHex();
                    int distance = -1,
                            heurDist;
                    for (Hexagon h : notAttacked) {
                        heurDist = this.admissibleHeuristic(h, goalHex);
                        if (closestSafe == null ||
                                heurDist < distance) {
                            distance = heurDist;
                            closestSafe = h;
                        }
                    }
                    return closestSafe;
            }
        }
    }

    private boolean traversable(Hexagon hex) {
        if (hex == null || hex.getTower() != null || (hex.getCreeps() != null && hex.getCreeps().size() > 0))
            return false;
        return true;
    }

    // get best guess estimate based on heuristic distance to goal
    private Hexagon estimateStep(Creep creep) {
        Hexagon current = creep.getHex(),
                prev = creep.getPrevHex();
        Point s = creep.getHex().getGridPosition();
        Point g = creep.getGoalHex().getGridPosition();

        int deltaX = g.x - s.x,
                deltaY = g.y - s.y;

        Hexagon neighbors[] = current.getNeighbors();

        if (deltaX == 0) {
            if(deltaY > 0) {
                int[] order = {0, 1, 5, 2, 4, 3};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                // y < 0
                int[] order = {3, 2, 4, 1, 5, 0};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
        }
        else if (deltaY == 0) {
            if(deltaX > 0) {
                int[] order = {5, 4, 0, 3, 1, 2};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                // x < 0
                int[] order = {1, 2, 0, 3, 5, 4};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
        }
        else if (deltaX > 0 && deltaY > 0) {
            if (deltaX > deltaY) {
                int[] order = {5, 0, 4, 3, 1, 2};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                int[] order = {0, 5, 4, 1, 2, 3};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }

        }
        else if (deltaX > 0 && deltaY < 0) {
            if (deltaX < (deltaY * -1)) {
                int[] order = {3, 4, 2, 5, 1, 0};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                int[] order = {4, 3, 5, 2, 0, 1};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
        }
        else if (deltaX < 0 && deltaY > 0) {
            if (deltaY >= (deltaX * -1)) {
                int[] order = {1, 0, 2, 5, 4, 3};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                int[] order = {1, 2, 0, 5, 3, 4};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
        }
        else if (deltaX < 0 && deltaY < 0) {
            if ((deltaX * -1) >= (deltaY * -1)) {
                int[] order = {2, 3, 1, 0, 4, 5};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
            else {
                int[] order = {3, 2, 4, 1, 5, 0};
                for (int i : order) {
                    if (this.traversable(neighbors[i]) && neighbors[i] != prev) {
                        return neighbors[i];
                    }
                }
            }
        }
        return null;
    }

    // estimate of creep will sustain X attacks in the next Y steps,
    // where X is the Threshold attributes and Y is the Foresight
    // attribute in the creep's genetic attributes
    // return true: will survive
    // false: will not, seek new path
    public boolean survivalEstimate(Creep creep) {
        Gene creepGene = creep.getAttr();
        int threshold = creepGene.getThreshold();
        int foresight = creepGene.getForesight();
        List<Hexagon> path = creep.getPath();

        int attackCount = 0;

        for (int i = 0; i < path.size() && i < foresight; i++) {
            if (path.get(i).getMyState() == Hexagon.STATE.ATTACKED) {
                attackCount++;
            }
        }

        if (attackCount >= threshold) {
            return false;
        }
        return true;

    }

    // check for random step
    private Hexagon takeRandomStep(Creep creep) {
        Hexagon current = creep.getHex(),
                goal = creep.getGoalHex();
        int chance = creep.getAttr().getRandomness() + 1;
        // randomness range = 1 to 4 / 512
        if ((int) (Math.random() * 512) <= chance) {
            Hexagon randomHex = this.estimateStep(creep);
            return this.estimateStep(creep);
        }
        return null;
    }
}
// JL: Original Code End