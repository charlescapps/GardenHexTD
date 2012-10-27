package com.ccapps.android.hextd.gamedata;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.animation.Animation;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/7/12
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticData {

    public static Bitmap BASIC_TOWER_IMAGE;
    public static Bitmap SUNFLOWER;
    public static Bitmap EGGPLANT;
    public static Bitmap CARNIVOROUS;
    public static Bitmap ROSE;

    public static Bitmap ANT;
    public static Bitmap DEAD_ANT;

    public static Bitmap ROTATE_ICON;

    public static List<Bitmap> SUNSHINE_ANIMATION;

    public static Display DEFAULT_SCREEN_SIZE;

    public static Map<Class<? extends Tower>, Integer> TOWER_COSTS;

    public static Logger l = Logger.getAnonymousLogger();

}
