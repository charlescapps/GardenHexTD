package com.ccapps.android.hextd.gamedata;

import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/10/12
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreepUtils {


    public static Creep getInstance(Class<? extends Creep> creepClass, Hexagon hex, Hexagon goalHex) {
        try {
            Constructor<? extends Creep> constructor = creepClass.getConstructor(Hexagon.class, Hexagon.class);
            return constructor.newInstance(hex, goalHex);
        } catch (Exception e) {
            StaticData.l.log(Level.SEVERE, "Exception in CreepUtils: " + e.getCause() + ": " + e.getCause().getMessage());
        }
        return null;
    }

    public static void addCreep(Class<? extends Creep> creepClass, int row, int col, Hexagon goalHex) {
        HexGrid GRID = HexGrid.getInstance();
        if (GRID.get(row, col).getCreep() == null) {
            Creep instance = getInstance(creepClass, GRID.get(row, col), goalHex);
            GRID.setCreep(row, col, instance);
        }

    }
}
