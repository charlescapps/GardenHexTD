package com.ccapps.android.hextd.gamedata;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.draw.CreepDrawable;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/15/12
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Creep {
    public int getDirection();
    public void setDirection(int direction);

    public Drawable getCreepDrawable();
    public void setCreepDrawable(CreepDrawable drawable);

    public List<Hexagon> getPath();
    public void setPath(List<Hexagon> path);

    public Hexagon getHex();
    public void setHex(Hexagon hex);

    public Hexagon getGoalHex();
    public void setGoalHex(Hexagon hex);

    public int getHitpoints();
    public void setHitpoints(int hp);

    public void initRoute();
    public void move();
    public void draw(Canvas canvas);

}
