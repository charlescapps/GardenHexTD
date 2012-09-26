package com.example.test.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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

            float xDelta = 100.f;
            float yDelta = 100.f;
            float a = 40.f;
            double sqrt2 = Math.sqrt(2.);
            PointF[] hexPoints = new PointF[6];
            hexPoints[0] = new PointF(-a+xDelta, 0.f+yDelta);
            hexPoints[1] = new PointF((-a/2.f)+xDelta, (float)(a*sqrt2/2.)+yDelta);
            hexPoints[2] = new PointF((a/2.f)+xDelta, (float)(a*sqrt2/2.)+yDelta);
            hexPoints[3] = new PointF(a+xDelta, 0.f+yDelta);
            hexPoints[4] = new PointF((a/2.f)+xDelta, (float)(-a*sqrt2/2.)+yDelta);
            hexPoints[5] = new PointF((-a/2.f)+xDelta, (float)(-a*sqrt2/2.)+yDelta);

            for (int i = 0; i < 6; i++) {
                c.drawLine(hexPoints[i].x, hexPoints[i].y, hexPoints[(i+1)%6].x, hexPoints[(i+1)%6].y, mPaint);
            }

            sh.unlockCanvasAndPost(c);
        }
    }
}
