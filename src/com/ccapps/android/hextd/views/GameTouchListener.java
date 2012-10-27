package com.ccapps.android.hextd.views;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.StaticData;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.ArrayList;
import java.util.List;
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
    private List<Runnable> towerMenuDelayEvents;
    private List<Runnable> towerInfoDelayEvents;

    public GameTouchListener(GameView.GameViewThread gameViewThread, View gameActivityView) {
        super();
        this.gameViewThread = gameViewThread;
        this.gameActivityView = gameActivityView;
        this.towerMenu = (TowerMenuView)gameActivityView.findViewById(R.id.towerMenuTable);
        this.towerInfoView = (TextView)gameActivityView.findViewById(R.id.towerInfoTextView);
        this.towerMenuDelayEvents = new ArrayList<Runnable>();
        this.towerInfoDelayEvents = new ArrayList<Runnable>();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX(), y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        //Can only show info popup if there's a tower
        if (clickedHex == null || clickedHex.getTower() == null) {
            towerInfoView.setVisibility(View.GONE);
            return false;
        }

        //Guarantee an old event doesn't prematurely hide the menu
        towerInfoDelayEvents.clear();
        Tower clickedTower = clickedHex.getTower();
        setTowerInfoDrawables(towerInfoView, clickedTower);

        towerInfoView.setOnClickListener(new OnClickRotate(clickedTower));

        Point towerCenter = clickedHex.getScreenCenter();
        setLayoutParams(towerInfoView, towerCenter.x, towerCenter.y);

        //Show the info menu
        towerInfoView.setVisibility(View.VISIBLE);
        towerInfoView.bringToFront();

        //Store the selected hexagon in the grid for drawing purposes
        GRID.setSelectedHexagon(clickedHex);
        clickedHex.setState(Hexagon.STATE.SELECTED);

        //Hide the menu in 3 seconds
        Runnable delayEvent = new Runnable() {
            @Override
            public void run() {
                if (towerInfoDelayEvents.contains(this)) {
                    towerInfoView.setVisibility(View.GONE);
                    towerInfoView.invalidate();
                }
            }
        };

        towerInfoDelayEvents.add(delayEvent);
        towerInfoView.postDelayed(delayEvent, 3000);

        return true;
    }

    /**
     * When a single tap is confirmed, bring up menu to add a tower to the screen.
     * @param e
     * @return
     */
    @Override
    public void onLongPress(MotionEvent e) {
        float x = e.getX(), y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        //Clicked off grid
        if (clickedHex == null) {
            return;
        }

        //Guarantee an old event doesn't prematurely hide the menu
        towerMenuDelayEvents.clear();

        towerMenu.setLastClickedHex(new Point(clickedHex.getGridPosition()));

        setLayoutParams(towerMenu, (int)clickedHex.getScreenCenter().x, (int)clickedHex.getScreenCenter().y);

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


        //Hide the menu in 3 seconds
        Runnable delayEvent = new Runnable() {
            @Override
            public void run() {
                if (towerMenuDelayEvents.contains(this)) {
                    towerMenu.setVisibility(View.GONE);
                    towerMenu.invalidate();
                }
            }
        };

        towerMenuDelayEvents.add(delayEvent);
        towerMenu.postDelayed(delayEvent, 3000);


    }

    private void setTowerInfoDrawables(TextView v, Tower t) {
        v.setText(t.getTowerName() + "\n" + t.getDamageString());


    }

    private void setLayoutParams(View v, int x, int y) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)v.getLayoutParams();
        int leftMargin = (int)(x + Hexagon.getGlobalSideLength());
        int topMargin = (int)(y + Hexagon.getGlobalSideLength());
        if (leftMargin + v.getMeasuredWidth() > StaticData.DEFAULT_SCREEN_SIZE.getWidth()) {
            leftMargin  -= (v.getMeasuredWidth()  );
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

    private class OnClickRotate implements View.OnClickListener {

        private Tower t;

        public OnClickRotate(Tower t) {
            this.t = t;
        }
        @Override
        public void onClick(View view) {
            t.rotateClockwise();
            towerInfoDelayEvents.clear();
        }
    }

}
