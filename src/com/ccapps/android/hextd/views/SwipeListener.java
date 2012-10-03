package com.ccapps.android.hextd.views;

import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/28/12
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SwipeListener extends GestureDetector.SimpleOnGestureListener {

    private GameView.GameThread gameThread;

    public SwipeListener(GameView.GameThread gameThread) {
        super();
        this.gameThread = gameThread;

    }
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        gameThread.postShiftGrid(-distanceX, -distanceY);
        return true;

    }

}
