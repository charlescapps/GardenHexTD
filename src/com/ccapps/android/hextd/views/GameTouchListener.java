package com.ccapps.android.hextd.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.ccapps.android.hextd.R;
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
    private View gameActivityView;
    private GridView towerMenu;

    public GameTouchListener(GameView.GameThread gameThread, View gameActivityView) {
        super();
        this.gameThread = gameThread;
        this.gameActivityView = gameActivityView;
        this.towerMenu = (GridView)gameActivityView.findViewById(R.id.tower_grid_menu);


    }

    /**
     * When a single tap is confirmed, bring up menu to add a tower to the screen.
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
//        Logger l = Logger.getAnonymousLogger();
//        Logger.getAnonymousLogger().log(Level.SEVERE, "Single tap happened!");
        float x = e.getX();
        float y = e.getY();
        HexGrid GRID = HexGrid.getInstance();
        Hexagon clickedHex = GRID.getHexFromCoords(x, y);

        if (clickedHex == null) {
//             l.log(Level.SEVERE, "Clicked OFF grid");
        } else {
//            l.log(Level.SEVERE, "Clicked ON grid");
//            l.log(Level.SEVERE, "Center = " + clickedHex.getGridPosition().x + " " + clickedHex.getGridPosition().y);
            int row = clickedHex.getGridPosition().x;
            int col = clickedHex.getGridPosition().y;
//            BasicTower basicTower = new BasicTower(clickedHex);
//            GRID.setTower(row, col, basicTower);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)towerMenu.getLayoutParams();
            lp.leftMargin = (int)x;
            lp.topMargin = (int)y;
            lp.bottomMargin = 0;
            lp.rightMargin = 0;
            towerMenu.setLayoutParams(lp);
//            towerMenu.offsetLeftAndRight((int)x - towerMenu.getLeft());
//            towerMenu.offsetTopAndBottom((int)y - towerMenu.getTop());

//            gameActivityView.invalidate();
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

    private static class MenuClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
//            (View)view.getParent().;
        }
    }

}
