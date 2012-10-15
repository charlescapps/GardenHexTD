package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.HexGrid;
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

    private int direction;
    private CreepDrawable creepDrawable;
    private List<Hexagon> path;
    private Hexagon hex;
    private Hexagon goalHex;
    private int hitpoints;

    public AntCreep(Hexagon hex, Hexagon goalHex) {
        this.hex = hex;
        this.goalHex = goalHex;
        this.direction = 0;
        this.hitpoints = 100;

        this.creepDrawable = new CreepDrawable(this, StaticData.ANT);

        initRoute();
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
    public void initRoute() {
        path = new ArrayList<Hexagon>();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon tmp = hex;
        Point goalPos = goalHex.getGridPosition();
        while (tmp != goalHex) {
            Point tmpPos = tmp.getGridPosition();
            Point newPos = new Point();
            if (tmpPos.x != goalPos.x) {
                newPos.y = tmpPos.y;
                newPos.x = tmpPos.x + (goalPos.x - tmpPos.x) / Math.abs(goalPos.x - tmpPos.x);
            } else {
                newPos.x = goalPos.x;
                newPos.y = tmpPos.y + (goalPos.y - tmpPos.y) / Math.abs(goalPos.y - tmpPos.y);
            }
            Hexagon candidate = GRID.get(newPos);

            path.add(GRID.get(newPos));
            tmp = GRID.get(newPos);
        }
    }

    @Override
    public void move() {
        if (path.size() <= 0) {
            return;
        }
        hex = path.remove(0);
        creepDrawable.updateLocation();
    }

    @Override
    public void draw(Canvas canvas) {
        creepDrawable.draw(canvas);
    }
}
