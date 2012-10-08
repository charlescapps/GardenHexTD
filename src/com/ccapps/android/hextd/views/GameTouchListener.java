package com.ccapps.android.hextd.views;

import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.BasicTower;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/28/12
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameTouchListener extends GestureDetector.SimpleOnGestureListener {

    private GameView.GameThread gameThread;

    public GameTouchListener(GameView.GameThread gameThread) {
        super();
        this.gameThread = gameThread;

    }

    /**
     * When a single tap is confirmed, bring up menu to add a tower to the screen.
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Logger l = Logger.getAnonymousLogger();
        Logger.getAnonymousLogger().log(Level.SEVERE, "Single tap happened!");
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(e.getX(), e.getY());

        if (clickedHex == null) {
             l.log(Level.SEVERE, "Clicked OFF grid");
        } else {
            l.log(Level.SEVERE, "Clicked ON grid");
            l.log(Level.SEVERE, "Center = " + clickedHex.getGridPosition().x + " " + clickedHex.getGridPosition().y);
            BasicTower basicTower = new BasicTower(clickedHex);
            GRID.setTower(clickedHex.getGridPosition().x, clickedHex.getGridPosition().y, basicTower);

        }

        return true;
    }

    @Override
    public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        gameThread.postShiftGrid(0.f, -distanceY);
        return true;

    }

}
