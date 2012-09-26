package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ccapps.android.hextd.draw.Hexagon;

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

            Hexagon hex = new Hexagon(new PointF(100.f, 100.f));

            hex.draw(c);

            Hexagon.setGlobalSideLength(20.f);
            Hexagon hex2 = new Hexagon(new PointF(250.f, 250.f));

            hex2.draw(c);

            sh.unlockCanvasAndPost(c);
        }
    }
}
