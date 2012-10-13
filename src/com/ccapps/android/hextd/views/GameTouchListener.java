package com.ccapps.android.hextd.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.*;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.StaticData;

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
    private View gameActivityView;
    private TowerMenuView towerMenu;
    private Display defaultScreenSize;

    public GameTouchListener(GameView.GameThread gameThread, View gameActivityView) {
        super();
        this.gameThread = gameThread;
        this.gameActivityView = gameActivityView;
        this.towerMenu = (TowerMenuView)gameActivityView.findViewById(R.id.towerMenuTable);
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
        float x = e.getX();
        float y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        if (clickedHex == null) {
             l.log(Level.SEVERE, "Clicked OFF grid");
        } else {

            int row = clickedHex.getGridPosition().x;
            int col = clickedHex.getGridPosition().y;
            towerMenu.setLastClickedHex(new Point(row, col));

            l.log(Level.SEVERE, "Clicked hex (" + row + ", " + col + ")");

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)towerMenu.getLayoutParams();
            int leftMargin = (int)(x + Hexagon.getGlobalSideLength()/2.f);
            int topMargin = (int)(y + Hexagon.getGlobalSideLength()/2.f);
            if (leftMargin + towerMenu.getWidth() > StaticData.DEFAULT_SCREEN_SIZE.getWidth()) {
                leftMargin  -= (towerMenu.getWidth() + Hexagon.getGlobalSideLength() );
            }
            if (topMargin + towerMenu.getHeight() > StaticData.DEFAULT_SCREEN_SIZE.getHeight()) {
                topMargin  -= (towerMenu.getHeight() + Hexagon.getGlobalSideLength() );
            }
            lp.leftMargin = leftMargin;
            lp.topMargin = topMargin;
            lp.bottomMargin = 0;
            lp.rightMargin = 0;
            towerMenu.setLayoutParams(lp);

            towerMenu.setVisibility(View.VISIBLE);
            towerMenu.bringToFront();

            gameActivityView.invalidate();



        }

        return true;
    }

    @Override
    public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        gameThread.postShiftGrid(0.f, -distanceY);
        return true;

    }

}
