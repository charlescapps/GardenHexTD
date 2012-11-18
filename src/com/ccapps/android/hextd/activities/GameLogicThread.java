package com.ccapps.android.hextd.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.SurfaceHolder;
import com.ccapps.android.hextd.algorithm.ScentAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.metagame.CreepGenerator;
import com.ccapps.android.hextd.views.GameView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class GameLogicThread extends Thread {
    private boolean isRunning;
    private static final long GAME_TICK = 250; // limit to 4 events/s reduce computations
    private GameView gameView;
    private HexGrid theGrid;
    private List<Tower> towersOnGrid;
    private List<Creep> creepsOnGrid;
    private CreepGenerator creepGenerator;

    public GameLogicThread(GameView gameView, CreepGenerator creepGenerator) {
        super();
        this.gameView = gameView;
        this.creepGenerator = creepGenerator;
        this.isRunning = false;
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
        int relativeTick = 0;
        while(true) {
            ++relativeTick;
            pauseMe(GAME_TICK);
            towersOnGrid = theGrid.getTowersOnGrid();
            creepsOnGrid = theGrid.getCreepsOnGrid();

            synchronized (towersOnGrid) {
                for (Tower t: towersOnGrid) {
                    t.attack();
                }
            }
            for (Creep c: creepsOnGrid) {
                c.move();
            }

            creepGenerator.tick();

            //Decay scents
            if (relativeTick % 64 == 0) {
                int[][] scents = ScentAlgorithm.scents;
                if (scents != null) {
                    for (int i = 0; i < theGrid.getNumVertical(); i++) {
                        for (int j = 0; j < theGrid.getNumHorizontal(); j++) {
                            if (scents[i][j] > 0) {
                                scents[i][j] -= 1 ;
                            }
                            else if (scents[i][j] < 0) {
                                scents[i][j] += 1;
                            }
                        }
                    }
                }
            }

            gameView.postNeedsDrawing();

            if (!this.isRunning) {
                suspendMe();
            }
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
            this.interrupt();
        }
    }
}
//CLC: Original Code End

