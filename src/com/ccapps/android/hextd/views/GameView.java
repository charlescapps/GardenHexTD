package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
        getHolder().addCallback(this);

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
            gameThread.shiftGrid(new PointF(dX, dY));
            invalidate();
            return true;
        }

        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        requestFocusFromTouch();
        gameThread = new GameThread(getHolder());
        gameThread.run();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class GameThread extends Thread {
        private SurfaceHolder sh;
        public GameThread(SurfaceHolder sh) {
            super();
            this.sh = sh;
            HexGrid.initHexGrid(new PointF(0.f, 0.f), 10, 20, 40.f);
            drawGrid();
        }

        public void drawGrid() {

            Canvas c = sh.lockCanvas();

            c.drawColor(Color.BLACK);

            HexGrid.getInstance().draw(c);

            sh.unlockCanvasAndPost(c);
        }

        public void shiftGrid(PointF delta) {
            HexGrid.shiftTopLeft(delta);

            drawGrid();
        }
    }
}
