package com.ccapps.android.hextd.algorithm;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Point;
import com.ccapps.android.hextd.ai.Pathfinder;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.gamedata.Creep;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 10/19/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AntCreepPathfinder implements CreepAlgorithm {

    private HexGrid GRID;
    private int pathCount[][];  // traversal count for open/closed paths in A-star
    private int pathCost[][];   // lowest path cost to point
    private Creep creep;

    public AntCreepPathfinder(Creep creep) {
        this.GRID = HexGrid.getInstance();
        this.creep = creep;

        int h = this.GRID.getNumHorizontal();
        int v = this.GRID.getNumVertical();

        this.pathCount = new int[v][h];
        this.pathCost = new int[v][h];
        // initialize path cost, count to zeroes
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {
                this.pathCount[i][j] = 0;
                this.pathCost[i][j] = 0;
            }
        }
    }

    @Override
    public boolean pathNeedsEvaluation() {
        List<Hexagon> currentPath = creep.getPath();
        if (currentPath == null || currentPath.size() > 0 && currentPath.get(0).getTower() != null) {
            return true;
        }
        return false;
    }

    // A* implementation of creep pathfind
    @Override
    public List<Hexagon> getPath(Hexagon curHex, Hexagon goalHex) {
        List<Hexagon> path = new ArrayList<Hexagon>();

        /* begin old algorithm
        Hexagon tempHex = curHex;
        Hexagon nextHex;
        int g = 0;  // path-cost estimate of A*
        int count = 0;
        HexGrid grid = HexGrid.getInstance();

        while (tempHex != goalHex && count < 40) {
            count++;
            Point tempPos = tempHex.getGridPosition();
            Point goalPos = goalHex.getGridPosition();
            Point nextPos = new Point();

            // x
            if (tempPos.x < goalPos.x) {
                nextPos.x = tempPos.x + 1;
            }
            else if (tempPos.x > goalPos.x) {
                nextPos.x = tempPos.x - 1;
            }
            else {
                nextPos.x = tempPos.x;
            }

            // y
            if (tempPos.y < goalPos.y) {
                nextPos.y = tempPos.y + 1;
            }
            else if (tempPos.y > goalPos.y) {
                nextPos.y = tempPos.y - 1;
            }
            else {
                nextPos.y = tempPos.y;
            }
            //path.add(GRID.get(nextPos));
            //tempHex = GRID.get(nextPos);

            for (int i = 0; i < 6; i++) {
                Hexagon neighbors[] = tempHex.getNeighbors();
                if (neighbors[(i + 4) % 6] != null) {
                    path.add(neighbors[i]);
                    tempHex = neighbors[i];
                    break;
                }
            }
        }
        return path;
         end old algorithm */

        return this.Astar(curHex, goalHex, 0);
    }

    @Override
    public Creep getCreep() {
        return creep;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCreep(Creep creep) {
        this.creep = creep;
    }

    // update game's hexgrid
    public void updateGrid(HexGrid newGrid) {
        this.GRID = newGrid;
    }

    // Heuristic estimate function of A*
    // make a shortest-path estimate to goal
    private int H(Hexagon curHex, Hexagon goalHex) {
        // heuristic estimate distance
        int distX,
            distY,
            distT;
        Point curPos = curHex.getGridPosition(),
                goalPos = goalHex.getGridPosition();

        distX = Math.abs(curPos.x - goalPos.x);
        distY = Math.abs(curPos.y - goalPos.y);

        if (distX >= 2 * distY)
            distT = distX;
        else
            distT = distX + distY - (distX / 2);

        return distT;
    }

    // recursive a-star function
    private List<Hexagon> Astar(Hexagon current, Hexagon goal, int g) {
        Hexagon neighbors[] = current.getNeighbors();
        List<Hexagon> shortestPath = new ArrayList<Hexagon>();

        // found goal
        if (current == goal) {
            shortestPath.add(current);
            return shortestPath;
        }

        // add neighbor hexagons to an ordered list, ascending by Heuristic Estimate
        List<Hexagon> hRankN = new ArrayList<Hexagon>();
        for (Hexagon hexN : neighbors) {
        //for (int i = 0; i < neighbors.length; i++) {
            if (hexN == null) {
                continue;
            }
            int hVal = this.H(hexN, goal);
            if (hRankN.isEmpty()) {
                hRankN.add(hexN);
            }
            else {
                boolean added = false;
                for (int j = 0; j < hRankN.size() && !added; j++) {
                    if (hVal <= this.H(hRankN.get(j), goal)) {
                        hRankN.add(j, hexN);
                        added = true;
                        //break;
                    }
                }
                if (!added) {   // add to end
                    hRankN.add(hexN);
                }
            }
        }

        // visit every neighbor
        for (Hexagon hexI : hRankN) {
            Point n = hexI.getGridPosition();
            List<Hexagon> tempPath = new ArrayList<Hexagon>();

            // TODO: add logical expression for "traversability" or "vacancy" of hex path
            if (pathCount[n.x][n.y] < 6) {  // open hex
                if (pathCount[n.x][n.y] == 0 || g < pathCost[n.x][n.y]) { // first visit or shortest path
                    pathCost[n.x][n.y] = g;

                    // visit neighbor in ascending order of heuristic estimate
                    tempPath.addAll(this.Astar(hexI, goal, g + 1));
                }
                pathCount[n.x][n.y]++;      // increment location count
            }
            if (tempPath.size() < shortestPath.size() && tempPath.size() > 0){
                shortestPath = tempPath;
            }
        }

        // path to goal found
        if (shortestPath.size() > 0 && shortestPath.get(shortestPath.size() - 1) == goal) {
            return shortestPath;
        }
        return null;
    }
}
