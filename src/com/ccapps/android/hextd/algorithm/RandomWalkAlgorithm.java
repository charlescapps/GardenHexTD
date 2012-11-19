package com.ccapps.android.hextd.algorithm;

import android.graphics.Point;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.*;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class RandomWalkAlgorithm implements CreepAlgorithm{

    private Creep creep;
    private Random random;
    private Set<Hexagon> visited;

    public RandomWalkAlgorithm() {
        this.random = new Random();
        visited = new TreeSet<Hexagon>();
    }

    public RandomWalkAlgorithm(Creep creep) {
        this.creep = creep;
        this.random = new Random();
        visited = new TreeSet<Hexagon>();
    }

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

    /**
     * Randomly choose adjacent Hexagons not in the current path until the goal is reached.
     *
     * @param src
     * @param goal
     * @return
     */
    @Override
    public List<Hexagon> buildPath(Hexagon src, Hexagon goal) {
        Hexagon next = null, previous = src;
        List<Hexagon> path = Collections.synchronizedList(new ArrayList<Hexagon>());
        HexGrid GRID = HexGrid.getInstance();

        while (next == null || !next.equals(goal)) {
            Point prevPos = previous.getGridPosition();

            //Attempt to go in the right X direction
            int xDir = (goal.getGridPosition().x - prevPos.x) > 0 ? 1 : -1;
            Hexagon candidate1 = GRID.get(prevPos.x + xDir, prevPos.y);

            //Attempt to go in the right Y direction
            int yDir = (goal.getGridPosition().y - prevPos.y) > 0 ? 1 : -1;
            Hexagon candidate2 = GRID.get(prevPos.x, prevPos.y + yDir);

            if (isGoodAndNotVisited(candidate1)) {
                next = candidate1;
            }
            else if (isGoodAndNotVisited(candidate2)) {
                next = candidate2;
            }
            else {
                //Otherwise go in random direction, preferring unvisited hexes
                ArrayList<Hexagon> notVisited = new ArrayList<Hexagon>();
                for (Hexagon h: previous.getNeighbors()) {
                    if (isGoodAndNotVisited(h)) {
                        notVisited.add(h);
                    }
                }
                if (notVisited.size() > 0){
                    next = notVisited.get(random.nextInt(notVisited.size()));
                }
                else {
                    do {
                        next = previous.getNeighbors()[random.nextInt(6)];
                    } while(!isGood(next));
                }
            }
            //Add next to path, and go to the next iteration
            synchronized (path) {
                path.add(next);
            }
            visited.add(next);
            previous = next;
        }
        return path;
    }

    @Override
    public Creep getCreep() {
        return creep;
    }

    @Override
    public void setCreep(Creep creep) {
        this.creep = creep;
    }

    private boolean isGood(Hexagon h) {
        return h != null && h.getTower() == null;
    }

    private boolean isGoodAndNotVisited(Hexagon h) {
        return h != null && h.getTower() == null && !visited.contains(h);
    }
}
//CLC: Original Code End
