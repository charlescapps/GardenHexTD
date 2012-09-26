package com.ccapps.android.hextd.draw;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/26/12
 * Time: 9:04 AM
 */
public class Hexagon extends Drawable {

    public static final PointF[] hexPoints;
    public static final float sqrt2;
    private static float sideLength = 40.f;

    static {
        sqrt2 = (float)Math.sqrt(2.);
        hexPoints = new PointF[6];
        sideLength = 40.f; //initial side length value
        setGlobalSideLength(sideLength);
    }

    /**
     * Set new side length and compute the relative coordinates of vertices based on the new length;
     *
     * @param newLength
     */
    public static void setGlobalSideLength(float newLength) {
        Hexagon.sideLength = newLength;
        hexPoints[0] = new PointF(-sideLength, 0.f);
        hexPoints[1] = new PointF((-sideLength/2.f), (float)(sideLength*sqrt2/2.));
        hexPoints[2] = new PointF((sideLength/2.f), (float)(sideLength*sqrt2/2.));
        hexPoints[3] = new PointF(sideLength, 0.f);
        hexPoints[4] = new PointF((sideLength/2.f), (float)(-sideLength*sqrt2/2.));
        hexPoints[5] = new PointF((-sideLength/2.f), (float)(-sideLength*sqrt2/2.));
    }

    public static float getGlobalSideLength() {
        return Hexagon.sideLength;
    }

    private Path hexPath;
    private Paint hexPaint;
    private PointF center;
    private Hexagon[] neighbors;

    public Hexagon(PointF center) {
        this.center = center;
        recomputePath();
        hexPaint = new Paint();
        hexPaint.setColor(Color.GREEN);
        hexPaint.setStrokeWidth(1);
        hexPaint.setStyle(Paint.Style.STROKE);
        //neighbors to be set by the HexGrid instance.
    }

    /**
     * Determines path based on current side length and center
     */
    private void recomputePath() {
        hexPath = new Path();
        hexPath.moveTo(hexPoints[0].x, hexPoints[0].y);
        for (int i = 0; i < 6; i++) {
            hexPath.lineTo(hexPoints[(i+1)%6].x, hexPoints[(i+1)%6].y);
        }
        hexPath.close();
        hexPath.offset(center.x, center.y);

    }

    @Override
    public void draw(Canvas canvas) {

        recomputePath();
        canvas.drawPath(hexPath, hexPaint);
    }

    public void setNeighbors(Hexagon[] neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public void setAlpha(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getOpacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
