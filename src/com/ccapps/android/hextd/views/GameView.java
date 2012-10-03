package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ccapps.android.hextd.activities.GameLogicThread;
import com.ccapps.android.hextd.draw.HexGrid;

import java.util.logging.Logger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private GestureDetector gestureDetector;
    private GameLogicThread gameLogicThread;
    private Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
        getHolder().addCallback(this);
    }

    public void setGameLogicThread(GameLogicThread gameLogicThread) {
        this.gameLogicThread = gameLogicThread;
    }

    public void stopDrawing() {
        this.gameThread.postSuspendMe();
    }

    public void startDrawing() {
        if (this.gameThread != null) {
            gameThread.unSuspendMe();
        }
    }

    public void postNeedsDrawing() {

        this.gameThread.postNeedsDrawing();

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean result = gestureDetector.onTouchEvent(e);
        if (!result) {
            if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                return true;
            }
            if (e.getActionMasked() == MotionEvent.ACTION_UP) {
                return true;
            }
            if (e.getActionMasked() == MotionEvent.ACTION_CANCEL){
                return true;
            }
        }

        return result;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        requestFocusFromTouch();
        gameThread = new GameThread(getHolder());
        gestureDetector = new GestureDetector(this.getContext(), new SwipeListener(gameThread));
        gameThread.start();
        gameLogicThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
       // gameThread.drawGrid();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * From docs, looks like if I do it this way w / a custom thread, there's no need to even pause the thread.
     * I can just draw "as fast as your thread is capable". http://developer.android.com/guide/topics/graphics/2d-graphics.html
     */
    public class GameThread extends Thread {
        private SurfaceHolder sh;
        private boolean needsDrawing;
        private PointF gridShiftValue;
        private boolean isRunning;
        private static final long PAUSE_TIME = 20; // limit to 60 fps, reduce computations
        private static final float VELOCITY_FACTOR = 1.5f;

        public GameThread(SurfaceHolder sh) {
            super();
            this.sh = sh;
            this.needsDrawing = true;
        }

        @Override
        public void run() {
            this.isRunning = true;
            eventLoop();
        }

        public void postSuspendMe() {
            this.isRunning = false;
        }

        private void drawGrid() {

            if (this.needsDrawing) {
                Canvas c = sh.lockCanvas();

                c.drawColor(Color.BLACK);

                HexGrid.getInstance().draw(c);

                sh.unlockCanvasAndPost(c);

                this.needsDrawing = false;
            }
        }

        private void shiftGrid() {
            if (this.gridShiftValue != null) {
                HexGrid.shiftTopLeft(gridShiftValue);
                this.gridShiftValue = null;
                this.needsDrawing = true;
            }
        }

        public void postShiftGrid(PointF delta) {
            delta.x *= VELOCITY_FACTOR;
            delta.y *= VELOCITY_FACTOR;
            this.gridShiftValue = delta;
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

        public void postNeedsDrawing() {
            this.needsDrawing = true;
        }

        /**
         * Wait, then shift the grid if necessary, then draw the grid
         */
        private void eventLoop() {

            while ( true ) {
                if (isRunning) {
                    pauseMe(PAUSE_TIME);
                    shiftGrid();
                    drawGrid();
                } else {
                    suspendMe();
                }
            }


        }


    }
}
