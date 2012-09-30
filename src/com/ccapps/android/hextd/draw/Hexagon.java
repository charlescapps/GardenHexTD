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

    /********************STATICS****************************/
    public static final PointF[] hexPoints;
    public static final float sqrt2;
    private static float sideLength ;
    public static PointF globalOffset;

    static {
        sqrt2 = (float)Math.sqrt(2.);
        hexPoints = new PointF[6];
        sideLength = 40.f; //initial side length value
        setGlobalSideLength(sideLength);
        globalOffset=new PointF(0.f, 0.f);
    }

    /**
     * Set new side length and compute the relative coordinates of a hexagon's vertices based on the new length;
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

        if (HexGrid.isInitialized()) {
            HexGrid.getInstance().initAllPaths();
        }
    }

    public static float getGlobalSideLength() {
        return Hexagon.sideLength;
    }

    public static void setGlobalOffset(PointF offset) {
        Hexagon.globalOffset = offset;
    }

    public static PointF getGlobalOffset() {
        return Hexagon.globalOffset;
    }

    /************************NON-STATICS************************************/

    private Path hexPath;
    private Paint hexPaint;
    private PointF center;
    private Hexagon[] neighbors;

    public Hexagon(PointF center) {
        this.center = center;
        initPath();
        hexPaint = new Paint();
        hexPaint.setColor(Color.GREEN);
        hexPaint.setStrokeWidth(1);
        hexPaint.setStyle(Paint.Style.STROKE);
        //neighbors to be set by the HexGrid instance.
    }

    /***********************DRAWING RELATED**************************/
    /**
     * Determines path based on current side length and center
     */
    public void invalidatePath(PointF delta) {
        hexPath.offset(delta.x, delta.y);
    }

    public void initPath() {
        hexPath = new Path();
        hexPath.moveTo(hexPoints[0].x, hexPoints[0].y);
        for (int i = 0; i < 6; i++) {
            hexPath.lineTo(hexPoints[(i+1)%6].x, hexPoints[(i+1)%6].y);
        }
        hexPath.close();
        hexPath.offset(center.x - Hexagon.globalOffset.x, center.y - Hexagon.globalOffset.y);

    }

    public void addPathTo(Path p) {
        p.addPath(this.hexPath);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(hexPath, hexPaint);
        canvas.drawPath(hexPath, hexPaint);
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

    /************************SETTERS / GETTERS************************************/
    public Hexagon[] getNeighbors() {
        return this.neighbors;
    }

    public void setNeighbors(Hexagon[] neighbors) {
        this.neighbors = neighbors;
    }


}
