package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ccapps.android.hextd.draw.HexGrid;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
        getHolder().addCallback(this);

    }

    public void stopDrawing() {
        this.gameThread.stopMe();
    }

    public void startDrawing() {
        if (this.gameThread != null) {
            synchronized (gameThread) {
                gameThread.notify();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        l.log(Level.INFO, "EVENT FOUND: " + e.toString());
        if (e.getAction() == MotionEvent.ACTION_DOWN ) {
            return true;
        }
        else if (e.getAction() == MotionEvent.ACTION_DOWN ) {
            return true;
        }
        else if ( e.getActionMasked() == MotionEvent.ACTION_MOVE ) {
            float dY = e.getY() - e.getRawY();
            float dX = e.getX() - e.getRawX();
            gameThread.postShiftGrid(new PointF(dX, dY));
            invalidate();
            return true;
        }

        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        requestFocusFromTouch();
        gameThread = new GameThread(getHolder());
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        gameThread.drawGrid();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
   // public void dispatchDraw()

    private class GameThread extends Thread {
        private SurfaceHolder sh;
        private PointF gridShiftValue;
        private boolean isRunning;
        private static final long PAUSE_TIME = 30; //about 30 frames per second

        public GameThread(SurfaceHolder sh) {
            super();
            this.sh = sh;
        }

        @Override
        public void run() {
            this.isRunning = true;
            HexGrid.initHexGrid(new PointF(0.f, 0.f), 10, 20, 40.f);
            eventLoop();
        }

        public void stopMe() {
            this.isRunning = false;
        }

        private void drawGrid() {

            if (HexGrid.isInitialized()) {
                Canvas c = sh.lockCanvas();

                c.drawColor(Color.BLACK);

                HexGrid.getInstance().draw(c);

                sh.unlockCanvasAndPost(c);
            }
        }

        public void postShiftGrid(PointF delta) {
            this.gridShiftValue = delta;
        }

        private void eventLoop() {

            while ( true ) {
                if (isRunning) {
                    try {
                        synchronized (this) {
                            this.wait(PAUSE_TIME);
                        }
                    } catch (InterruptedException e) {
                        //pwn
                    }
                    drawGrid();
                    shiftGrid();
                } else {
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            //pwn
                        }
                    }
                }
            }


        }

        private void shiftGrid() {
            if (gridShiftValue != null) {
                HexGrid.shiftTopLeft(gridShiftValue);
                gridShiftValue = null;
            }
        }


    }
}
