package com.ccapps.android.hextd.algorithm;

import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/20/12
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CreepAlgorithm {
    public boolean pathNeedsEvaluation();
    public List<Hexagon> buildPath(Hexagon src, Hexagon goal);

    public Creep getCreep();
    public void setCreep(Creep creep);

}
