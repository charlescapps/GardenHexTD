package com.ccapps.android.hextd.ai;

import android.graphics.Point;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 10/19/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AntCreepPathfinder implements Pathfinder {

    private HexGrid GRID;
    private int pathCount[][];  // traversal count for open/closed paths in A-star
    private int pathCost[][];   // lowest path cost to point

    public AntCreepPathfinder() {
        this.GRID = HexGrid.getInstance();
        this.pathCount = new int[this.GRID.getNumHorizontal()][this.GRID.getNumVertical()];
        this.pathCost = new int[this.GRID.getNumHorizontal()][this.GRID.getNumVertical()];
        // initialize path cost, count to zeroes
        for (int i = 0; i < this.GRID.getNumHorizontal(); i++) {
            for (int j = 0; i < this.GRID.getNumVertical(); j++) {
                pathCount[i][j] = 0;
                pathCost[i][j] = 0;
            }
        }


    }

    // A* implementation of creep pathfind
    @Override
    public List<Hexagon> getPath(Hexagon curHex, Hexagon goalHex) {
        ArrayList<Hexagon> path = new ArrayList<Hexagon>();
        Hexagon tempHex = curHex;
        Hexagon nextHex;
        int g = 0;  // path-cost estimate of A*
        int count = 0;

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
        //return this.Astar(curHex, goalHex, 0);
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
    private ArrayList<Hexagon> Astar(Hexagon current, Hexagon goal, int g) {
        Hexagon neighbors[] = current.getNeighbors();
        ArrayList<Hexagon> shortestPath = new ArrayList<Hexagon>();

        // found goal
        if (current == goal) {
            shortestPath.add(current);
            return shortestPath;
        }


        // add neighbor hexagons to an ordered list, ascending by Heuristic Estimate
        ArrayList<Hexagon> hRankN = new ArrayList<Hexagon>();
        for (int i = 0; i < neighbors.length; i++) {
            int hVal = this.H(neighbors[i], goal);
            if (hRankN.isEmpty()) {
                hRankN.add(neighbors[i]);
            }
            else {
                boolean added = false;
                for (int j = 0; j < hRankN.size(); j++) {
                    if (hVal <= this.H(hRankN.get(j), goal)) {
                        hRankN.add(j, neighbors[i]);
                        added = true;
                    }
                }
                if (added == false) {   // add to end
                    hRankN.add(neighbors[i]);
                }
            }
        }

        // visit every neighbor
        for (int i = 0; i < hRankN.size(); i++) {
            Point n = hRankN.get(i).getGridPosition();
            ArrayList<Hexagon> tempPath = new ArrayList<Hexagon>();

            // TODO: add logical expression for "traversability" or "vacancy" of hex path
            if (pathCount[n.x][n.y] < 6) {  // open hex
                if (pathCount[n.x][n.y] == 0 || g < pathCost[n.x][n.y]) { // first visit or shortest path
                    pathCost[n.x][n.y] = g;

                    // visit neighbor in ascending order of heuristic estimate
                    tempPath.addAll(this.Astar(hRankN.get(i), goal, g + 1));
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
