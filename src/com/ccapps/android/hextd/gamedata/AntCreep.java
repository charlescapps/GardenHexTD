package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.List;
import static com.ccapps.android.hextd.gamedata.Creep.FORAGE_STATE;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class AntCreep implements Creep {

    protected int direction;
    protected int speed;
    private CreepDrawable creepDrawable;
    protected List<Hexagon> path;
    protected Hexagon hex;
    protected Hexagon goalHex;
    protected Hexagon sourceHex;
    protected int hitpoints;
    protected CreepAlgorithm algorithm;
    protected int tick;
    protected FORAGE_STATE forageState;

    private State creepState;
    private Gene attr;
    private boolean goalMet;
    private int stepCount;

    protected List<Hexagon> prevPath;

    public AntCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm) {
        this.hex = hex;
        this.goalHex = goalHex;
        this.sourceHex = hex;
        this.algorithm = algorithm;
        this.algorithm.setCreep(this);
        this.direction = 0;
        this.hitpoints = 250;

        this.creepDrawable = new CreepDrawable(this, StaticData.ANT, StaticData.DEAD_ANT);
        this.tick = 0;
        this.speed = 4;
        this.forageState = FORAGE_STATE.FORAGE;

        this.path = null;
        this.prevPath = null;

        this.creepState = State.FORAGE_FOLLOW;
        this.goalMet = false;
        this.stepCount = 0;

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
    public void evaluateRoute() {
        if (hitpoints > 0 && algorithm.pathNeedsEvaluation()) {
            this.path = algorithm.buildPath(hex, goalHex);
        }
    }

    @Override
    public void move() {
        if (++tick % speed == 0 && hitpoints > 0) {
            evaluateRoute();
            if (path == null || path.size() <= 0) {
                return;
            }
            hex.removeCreep(this);
            hex = path.remove(0);
            hex.setCreep(this);
            creepDrawable.updateLocation();
        }
    }

    @Override
    public State getState()
    {
        return this.creepState;
    }

    @Override
    public void setState(State creepState) {
        this.creepState = creepState;
    }

    @Override
    public boolean equals(Object o) {
        return this==o;
    }

    @Override
    public int getStepCount() {
        return this.stepCount;
    }

    @Override
    public void incStepCount() {
        this.stepCount++;
    }

    @Override
    public void setGoalMet(boolean goalMet) {
        this.goalMet = goalMet;
    }

    @Override
    public boolean getGoalMet() {
        return this.goalMet;
    }

    @Override
    public Gene getAttr() {
        return this.attr;
    }

    @Override
    public Hexagon getPrevHex() {
        return this.prevPath.get(0);
    }
}
//CLC: Original Code End
