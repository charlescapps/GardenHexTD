package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/20/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CreepGenerator {
    public void tick();

    public int getSpeed();
    public void setSpeed(int speed);

    public List<Hexagon> getSourceHexes();
    public void setSourceHexes(List<Hexagon> sourceHexes);

    public void addCreepToQueue(Creep c);
}
