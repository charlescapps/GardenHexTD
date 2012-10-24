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
    private final TextView towerInfoView;

    public GameTouchListener(GameView.GameViewThread gameViewThread, View gameActivityView) {
        super();
        this.gameViewThread = gameViewThread;
        this.gameActivityView = gameActivityView;
        this.towerMenu = (TowerMenuView)gameActivityView.findViewById(R.id.towerMenuTable);
        this.towerInfoView = (TextView)gameActivityView.findViewById(R.id.towerInfoTextView);

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX(), y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        //Can only show info popup if there's a tower
        if (clickedHex == null || clickedHex.getTower() == null) {
            return true;
        }

        setLayoutParams(towerInfoView, (int)x, (int)y);

        //Show the info menu
        towerInfoView.setVisibility(View.VISIBLE);
        towerInfoView.bringToFront();

        //Store the selected hexagon in the grid for drawing purposes
        GRID.setSelectedHexagon(clickedHex);
        clickedHex.setState(Hexagon.STATE.SELECTED);

        //Hide the menu in 3 seconds
        towerInfoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                towerInfoView.setVisibility(View.GONE);
                towerInfoView.invalidate();
            }
        }
        , 3000);

        return true;
    }

    /**
     * When a single tap is confirmed, bring up menu to add a tower to the screen.
     * @param e
     * @return
     */
    @Override
    public void onLongPress(MotionEvent e) {
        //Guarantee an old event doesn't prematurely hide the menu
        towerMenu.clearDelayedEvents();

        float x = e.getX(), y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        //Clicked off grid
        if (clickedHex == null) {
            return;
        }

        towerMenu.setLastClickedHex(new Point(clickedHex.getGridPosition()));

        setLayoutParams(towerMenu, (int)x, (int)y);

        //Hide the tower info menu if it is visible
        if (towerInfoView.getVisibility() == View.VISIBLE) {
            towerInfoView.setVisibility(View.GONE);
        }

        //Make this menu visible
        towerMenu.setVisibility(View.VISIBLE);
        towerMenu.bringToFront();

        //Store the selected hexagon in the grid for drawing
        GRID.setSelectedHexagon(clickedHex);
        clickedHex.setState(Hexagon.STATE.SELECTED);

        gameActivityView.invalidate();

        //Hide in 3 seconds.
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

    private void setLayoutParams(View v, int x, int y) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)v.getLayoutParams();
        int leftMargin = (int)(x + Hexagon.getGlobalSideLength());
        int topMargin = (int)(y + Hexagon.getGlobalSideLength());
        if (leftMargin + v.getMeasuredWidth() > StaticData.DEFAULT_SCREEN_SIZE.getWidth()) {
            leftMargin  -= (v.getMeasuredWidth() + 2.f*Hexagon.getGlobalSideLength() );
        }
        if (topMargin + v.getMeasuredHeight() + 30.f > StaticData.DEFAULT_SCREEN_SIZE.getHeight()) {
            topMargin  -= (v.getMeasuredHeight() + 2.f*Hexagon.getGlobalSideLength() );
        }
        lp.leftMargin = leftMargin;
        lp.topMargin = topMargin;
        lp.bottomMargin = 0;
        lp.rightMargin = 0;
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        gameViewThread.postShiftGrid(0.f, -distanceY);
        return true;

    }

}
