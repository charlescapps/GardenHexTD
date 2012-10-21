package com.ccapps.android.hextd.views;

import android.graphics.Point;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
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

    private GameView.GameViewThread gameViewThread;
    private View gameActivityView;
    private TowerMenuView towerMenu;

    public GameTouchListener(GameView.GameViewThread gameViewThread, View gameActivityView) {
        super();
        this.gameViewThread = gameViewThread;
        this.gameActivityView = gameActivityView;
        this.towerMenu = (TowerMenuView)gameActivityView.findViewById(R.id.towerMenuTable);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(e.getX(), e.getY());
        if (clickedHex.getTower() == null) {
            return false;
        }
        TextView towerInfoView = (TextView)gameActivityView.findViewById(R.id.towerInfoTextView);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)towerMenu.getLayoutParams();
        int leftMargin = (int)(x + Hexagon.getGlobalSideLength()/2.f);
        int topMargin = (int)(y + Hexagon.getGlobalSideLength()/2.f);
        if (leftMargin + towerMenu.getWidth() > StaticData.DEFAULT_SCREEN_SIZE.getWidth()) {
            leftMargin  -= (towerMenu.getWidth() + Hexagon.getGlobalSideLength() );
        }
        if (topMargin + towerMenu.getHeight() - HexGrid.GLOBAL_OFFSET.y > StaticData.DEFAULT_SCREEN_SIZE.getHeight()) {
            topMargin  -= (towerMenu.getHeight() + Hexagon.getGlobalSideLength() );
        }
        lp.leftMargin = leftMargin;
        lp.topMargin = topMargin + towerMenu.getYOffset();
        lp.bottomMargin = 0;
        lp.rightMargin = 0;
        towerInfoView.setLayoutParams(lp);

        towerInfoView.setVisibility(View.VISIBLE);
        towerInfoView.bringToFront();
        GRID.setSelectedHexagon(clickedHex);

        return true;
    }

    /**
     * When a single tap is confirmed, bring up menu to add a tower to the screen.
     * @param e
     * @return
     */
    @Override
    public void onLongPress(MotionEvent e) {
        towerMenu.clearDelayedEvents();
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
            if (topMargin + towerMenu.getHeight() - HexGrid.GLOBAL_OFFSET.y > StaticData.DEFAULT_SCREEN_SIZE.getHeight()) {
                topMargin  -= (towerMenu.getHeight() + Hexagon.getGlobalSideLength() );
            }
            lp.leftMargin = leftMargin;
            lp.topMargin = topMargin + towerMenu.getYOffset();
            lp.bottomMargin = 0;
            lp.rightMargin = 0;
            towerMenu.setLayoutParams(lp);

            towerMenu.setVisibility(View.VISIBLE);
            towerMenu.bringToFront();

            GRID.setSelectedHexagon(clickedHex);
            clickedHex.setState(Hexagon.STATE.SELECTED);

            gameActivityView.invalidate();

            towerMenu.postDelayed(towerMenu.addDelayedEvent(new Runnable() {
                    @Override
                    public void run() {
                        if (towerMenu.containsEvent(this)) {
                            towerMenu.setVisibility(View.GONE);
                            towerMenu.invalidate();
                        }
                    }
                })
            , 3000);


        }
    }

    @Override
    public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        gameViewThread.postShiftGrid(0.f, -distanceY);
        return true;

    }

}
