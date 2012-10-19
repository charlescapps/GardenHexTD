package com.ccapps.android.hextd.ai;

import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 10/15/12
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Pathfinder {

    List<Hexagon> getPath();

}
