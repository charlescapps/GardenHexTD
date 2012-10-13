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
public class TowerUtils {


    public static Tower getInstance(Class<? extends Tower> towerClass, Hexagon hex) {
        try {
            Constructor<? extends Tower> constructor = towerClass.getConstructor(Hexagon.class);
            return constructor.newInstance(hex);
        } catch (Exception e) {
            StaticData.l.log(Level.SEVERE, "Exception in TowerUtils: " + e.getCause() + ": " + e.getCause().getMessage());
        }
        return null;
    }

    public static void addTower(Class<? extends Tower> towerClass, int row, int col) {
        HexGrid GRID = HexGrid.getInstance();
        if (GRID.get(row, col).getTower() == null) {
            Tower instance = getInstance(towerClass, GRID.get(row, col));
            GRID.setTower(row, col, instance);
        }

    }
}
