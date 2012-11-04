package com.ccapps.android.hextd.gamedata;

import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.Hexagon;

/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 11/4/12
 * Time: 3:07 PM
 * This class represents an ant creep with state implementation
 */
public class AntStateCreep extends AntCreep {

    public AntStateCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm) {

        super(hex, goalHex, algorithm);

    }

}
