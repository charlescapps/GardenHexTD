package com.example.test.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(getHolder());
        gameThread.run();

        //To change body of implemented methods use File | Settings | File Templates.
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
        public GameThread(SurfaceHolder sh) {
            super();

            Canvas c = sh.lockCanvas();

            Paint mPaint = new Paint();
            mPaint.setColor(Color.GREEN);
            mPaint.setStrokeWidth(2);
            c.drawCircle(150, 150, 80, mPaint);

            sh.unlockCanvasAndPost(c);
        }
    }
}
