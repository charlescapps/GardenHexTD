package com.ccapps.android.hextd.draw;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/26/12
 * Time: 10:57 AM
 */

/**
 * Singleton design pattern.
 */
public class HexGrid extends Drawable {

    //STATICS
    private static HexGrid GRID;

    /**
     * Must be called before getting an instance...
     *
     * @param topLeft
     * @param numHorizontal
     * @param numVertical
     */
    public static void initHexGrid(PointF topLeft, int numHorizontal, int numVertical, float sideLength ) {
        HexGrid.GRID = new HexGrid(topLeft, numHorizontal, numVertical);
        Hexagon.setGlobalSideLength(sideLength);
    }

    public static void shiftTopLeft(PointF delta) {
        if (HexGrid.isInitialized()) {
            GRID.topLeft.x += delta.x;
            GRID.topLeft.y += delta.y;
            PointF currentOffset = Hexagon.getGlobalOffset();
            Hexagon.setGlobalOffset(new PointF(delta.x + currentOffset.x, delta.y + currentOffset.y ));
            GRID.invalidateAllPaths();
        }
    }

    public static boolean isInitialized() {
        return GRID != null;
    }

    public static HexGrid getInstance()  {
        if (GRID==null) {
            throw new RuntimeException("Must init hex grid before getting the instance.");
        }
        return HexGrid.GRID;
    }

    //DATA
    private PointF topLeft;
    private int numHorizontal;
    private int numVertical;
    /**
     * A hex grid is laid out basically like a square grid w/ more connections.
     * Will create documentation eventually w/ pretty diagrams.
     */
    private Hexagon[][] hexMatrix;

    /**
     *
     * @param topLeft - coordinates of the bottom-left hexagon
     * @param numHorizontal - the number of hexagons left-to-right
     * @param numVertical - the number of hexagons top-to-bottom
     */
    private HexGrid(PointF topLeft, int numHorizontal, int numVertical) {

        this.topLeft = topLeft;
        float a = Hexagon.getGlobalSideLength();
        float h = a*Hexagon.sqrt2/2.f;

        hexMatrix = new Hexagon[numVertical][numHorizontal];  //rows / columns starting from top-left (math matrix style)

        //Generate the appropriate hexagons
        for (int j = 0; j < numVertical; j++) {
            for (int i = 0; i < numHorizontal; i++) {
                float hOffset = 3.f*a*(float)i/2.f;
                float vOffset;
                if (i % 2 == 0) {
                    vOffset = 2.f*h*(float)j;
                } else {
                    vOffset = 2.f*h*(float)j - h;
                }
                hexMatrix[j][i] = new Hexagon(new PointF(topLeft.x + hOffset, topLeft.y + vOffset));
            }
        }
    }

    public void invalidateAllPaths() {
        for (Hexagon[] hs: hexMatrix) {
            for (Hexagon h: hs) {
                h.invalidatePath();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Hexagon[] hs: hexMatrix) {
            for (Hexagon h: hs) {
                h.draw(canvas);
            }
        }
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
