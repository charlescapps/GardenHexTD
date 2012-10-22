package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/15/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AntCreep implements Creep {


    private creepStatus status;
    private int direction;
    private int speed;
    private CreepDrawable creepDrawable;
    private List<Hexagon> path;
    private Hexagon hex;
    private Hexagon prevHex;
    private Hexagon goalHex;
    private int hitpoints;
    private CreepAlgorithm algorithm;
    private int tick;



    public AntCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm) {
        this.prevHex = null;
        this.hex = hex;
        this.goalHex = goalHex;
        this.algorithm = algorithm;
        this.algorithm.setCreep(this);
        this.direction = 0;
        this.hitpoints = 500;

        this.creepDrawable = new CreepDrawable(this, StaticData.ANT, StaticData.DEAD_ANT);
        this.tick = 0;
        this.speed = 4;
        this.status = creepStatus.FOR_FOL;

        this.evaluateRoute();
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public CreepDrawable getCreepDrawable() {
        return creepDrawable;
    }

    @Override
    public void setCreepDrawable(CreepDrawable creepDrawable) {
        this.creepDrawable = creepDrawable;
    }

    @Override
    public List<Hexagon> getPath() {
        return path;
    }

    @Override
    public void setPath(List<Hexagon> path) {
        this.path = path;
    }

    @Override
    public Hexagon getHex() {
        return hex;
    }

    @Override
    public void setHex(Hexagon hex) {
        this.hex = hex;
    }

    @Override
    public Hexagon getGoalHex() {
        return goalHex;
    }

    @Override
    public void setGoalHex(Hexagon goalHex) {
        this.goalHex = goalHex;
    }

    @Override
    public int getHitpoints() {
        return hitpoints;
    }

    @Override
    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    @Override
    public void loseHitpoints(int hp) {
        this.hitpoints -= hp;
    }

    @Override
    public boolean isDead() {
        return hitpoints <= 0;
    }

    @Override
    public CreepAlgorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void setAlgorithm(CreepAlgorithm creepAlgorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void evaluateRoute() {   // much of this could be placed in algorithm.pathNeedsEvalution
        if (hitpoints <= 0)
            return;
        if (this.status == creepStatus.FOR_LEAD) {  // already leading
            if (algorithm.pathNeedsEvaluation()) {
                this.path = algorithm.buildPath(this.hex, this.goalHex);
            }
        }
        else if (this.status == creepStatus.FOR_FOL) {
            // determine if creep should lead
            Hexagon neighbors[] = this.hex.getNeighbors();
            List<Hexagon> candidate = new ArrayList<Hexagon>(); // possible path
            int maxWt = 0;
            for(Hexagon n : neighbors) {
                if (n != null) {
                    int wt = n.getCreepWeight();
                    if (wt > maxWt && n != this.prevHex) {
                        maxWt = wt;
                        candidate.clear();
                        candidate.add(n);
                    }
                    else if (wt == maxWt && maxWt > 0 && n != this.prevHex) {
                        candidate.add(n);
                    }
                }
            }
            if (candidate.isEmpty() && this.path == null) {    // time to lead
                this.status = creepStatus.FOR_LEAD;
                this.path = algorithm.buildPath(hex, goalHex);
            }
            else {  // follower, choose a candidate
                if (candidate.size() > 1) {
                    // randomly decide
                    Hexagon tempHex = candidate.get((int)(Math.random() * candidate.size()));
                    candidate.clear();
                    candidate.add(tempHex);
                }
                this.status = creepStatus.FOR_FOL;
                this.path = candidate;
            }
        }
    }

    @Override
    public void move() {
        if (++tick % speed == 0 && hitpoints > 0) {
            if (path.size() <= 0) {
                return;
            }
            evaluateRoute();
            hex.setCreep(null);
            hex.setCreepWeight(10);
            this.prevHex = hex;
            hex = path.remove(0);
            hex.setCreep(this);
            creepDrawable.updateLocation();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        creepDrawable.draw(canvas);
    }

    // status getter/setter
    @Override
    public creepStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(creepStatus status) {
        this.status = status;
    }

    @Override
    public Hexagon getPrevHex() {
        return this.prevHex;
    }
}
