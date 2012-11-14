package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 11/4/12
 * Time: 3:07 PM
 * This class represents an ant creep with state implementation
 */

public class AntStateCreep extends AntCreep {

    private State creepState;
    private Hexagon prevHex;

    protected int direction;
    protected int speed;
    protected CreepDrawable creepDrawable;
    protected List<Hexagon> path;
    protected Hexagon hex;
    protected Hexagon goalHex;
    protected Hexagon sourceHex;
    protected int hitpoints;
    protected CreepAlgorithm algorithm;
    protected int tick;

    public AntStateCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm) {
        this.hex = hex;
        this.goalHex = goalHex;
        this.sourceHex = hex;
        this.algorithm = algorithm;
        this.algorithm.setCreep(this);
        this.direction = 0;
        this.hitpoints = 500;

        this.creepDrawable = new CreepDrawable(this, StaticData.ANT, StaticData.DEAD_ANT);
        this.tick = 0;
        this.speed = 4;

        // default state upon spawning
        this.creepState = State.FORAGE_FOLLOW;
        this.prevHex = null;

        evaluateRoute();
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
    public Hexagon getSourceHex() {
        return sourceHex;
    }

    @Override
    public void setSourceHex(Hexagon hex) {
        this.sourceHex = hex;
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
    public void draw(Canvas canvas) {
        creepDrawable.draw(canvas);
    }

    @Override
    public void move() {
        if (++tick % speed == 0 && hitpoints > 0) {
            if (path.size() <= 0) {
                this.determineState();
            }
            this.evaluateRoute();
            hex.setCreep(null);
            hex = path.remove(0);
            hex.setCreep(this);
            creepDrawable.updateLocation();
        }
    }

    @Override
    public void evaluateRoute() {
        if (this.creepState == State.FORAGE_FOLLOW) {
            return;
        }
        if (this.hitpoints > 0 && this.algorithm.pathNeedsEvaluation() &&
            this.creepState == State.FORAGE_LEAD) {
            this.path = this.algorithm.buildPath(this.hex, this.goalHex);
        }
    }

    private void determineState() {
        Hexagon neighbors[] = this.hex.getNeighbors();
        List<Hexagon> candidates = new ArrayList<Hexagon>(6);
        int maxWeight = -100;

        for (Hexagon h : neighbors) {
            if (h != null) {
                if (h == prevHex)
                    continue;
                int weight = h.getWeight();
                if (weight > maxWeight) {
                    candidates.clear();
                    maxWeight = h.getWeight();
                    candidates.add(h);
                }
                else if (weight == maxWeight) {
                    candidates.add(h);
                }
            }
        }

        // set to lead
        if (maxWeight <= 0) {
            this.creepState = State.FORAGE_LEAD;
        }
        else { // weight > 0
            if (candidates.size() == 1) {
                this.path = candidates;
            }
        }

    }

    @Override
    public State getState()
    {
        return this.creepState;
    }

    @Override
    public void setState(State state) {
        this.creepState = state;
    }

    @Override
    public Hexagon getPrevHex() {
        return this.prevHex;
    }

}
