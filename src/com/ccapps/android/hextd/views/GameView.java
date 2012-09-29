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
import com.ccapps.android.hextd.draw.HexGrid;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private GestureDetector gestureDetector;
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
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
       // gameThread.drawGrid();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
   // public void dispatchDraw()

    public class GameThread extends Thread {
        private SurfaceHolder sh;
        private Canvas canvas;
        private PointF gridShiftValue;
        private boolean isRunning;
        private static final long PAUSE_TIME = 50; //about 60 frames per second
        private static final float VELOCITY_FACTOR = 1.2f;

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
            delta.x *= VELOCITY_FACTOR;
            delta.y *= VELOCITY_FACTOR;
            HexGrid.shiftTopLeft(delta);
            //GameView.this.invalidate();
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


    }
}
