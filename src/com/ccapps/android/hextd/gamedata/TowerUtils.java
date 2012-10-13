package com.ccapps.android.hextd.gamedata;

import com.ccapps.android.hextd.draw.Hexagon;

import java.lang.reflect.Constructor;

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
            //you be pwned
        }
        return null;
    }
}
