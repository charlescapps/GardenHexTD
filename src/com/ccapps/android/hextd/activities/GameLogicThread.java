package com.ccapps.android.hextd.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.SurfaceHolder;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.views.GameView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/3/12
 * Time: 7:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameLogicThread extends Thread {
    private boolean isRunning;
    private static final long GAME_TICK = 250; // limit to 4 events/s reduce computations
    private GameView gameView;
    private HexGrid theGrid;
    private List<Tower> towersOnGrid;

    public GameLogicThread(GameView gameView) {
        super();
        this.gameView = gameView;
        this.isRunning = true;
        this.theGrid = HexGrid.getInstance();
        this.towersOnGrid = theGrid.getTowersOnGrid();
    }

    @Override
    public void run() {
        this.isRunning = true;
        eventLoop();
    }

    public void postSuspendMe() {
        this.isRunning = false;
    }

    private void eventLoop() {
        while(true) {
            pauseMe(GAME_TICK);
            for (Tower t: towersOnGrid) {
                t.attack();
            }
            gameView.postNeedsDrawing();
        }

    }

    private void pauseMe(long time) {
        try {
            synchronized (this) {
                this.wait(time);
            }
        } catch (InterruptedException e) {
            //pwn
        }
    }

    private void suspendMe() {
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            //pwn
        }
    }

    public void unSuspendMe() {
        this.isRunning = true;
        synchronized (this) {
            this.notify();
        }
    }
}
