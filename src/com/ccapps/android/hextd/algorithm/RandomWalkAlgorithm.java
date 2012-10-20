package com.ccapps.android.hextd.algorithm;

import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/20/12
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomWalkAlgorithm implements CreepAlgorithm{

    private Creep creep;
    private Random random;

    public RandomWalkAlgorithm() {
        this.random = new Random();
    }

    public RandomWalkAlgorithm(Creep creep) {
        this.creep = creep;
        this.random = new Random();
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

        while (next == null || !next.equals(goal)) {
            do {
                next = previous.getNeighbors()[random.nextInt(6)];
            } while(next == null || next.getTower() != null);
            synchronized (path) {
                path.add(next);
            }
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
}
