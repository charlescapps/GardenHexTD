package com.ccapps.android.hextd.draw;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        GRID.topLeft.x += delta.x;
        GRID.topLeft.y += delta.y;
        Hexagon.globalOffset.x += delta.x;
        Hexagon.globalOffset.y += delta.y;

        GRID.gridPath.offset(delta.x, delta.y);
        //  GRID.invalidateAllPaths(delta);

    }

    public static boolean isInitialized() {
        return GRID != null;
    }

    public static HexGrid getInstance()  {
        return HexGrid.GRID;
    }

    //DATA
    private PointF topLeft;
    private int numHorizontal;
    private int numVertical;
    private Path gridPath;
    private Paint gridPaint; 
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
        this.gridPath = new Path();
        gridPaint = new Paint();
        gridPaint.setColor(Color.GREEN);
        gridPaint.setStrokeWidth(1);
        gridPaint.setStyle(Paint.Style.STROKE);
        
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
                hexMatrix[j][i].addPathTo(this.gridPath);
            }
        }

        //link up hexagons
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                Hexagon[] nbrs = new Hexagon[6];
                int numFound = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ( l == 0 && k == 0 ) { //no edge to self
                            continue;
                        }
                        //out of bounds
                        if (i+k < 0 || i + k > numVertical - 1 || j + l < 0 || j + l > numHorizontal - 1) {
                            continue;
                        }
                        //Even columns only corner links in down a row
                        if ( k == -1 && ( l == 1 ||  l == -1) && j % 2 == 0 ) {
                            continue;
                        }
                        //Odd columns only have corner links up a row
                        if ( k == 1 && ( l == 1 || l == -1) && j % 2 == 1 ) {
                            continue;
                        }
                        nbrs[numFound++] = hexMatrix[i+k][j+l];
                       // Verifying the proper edges are created!
                       // Logger.getAnonymousLogger().log(Level.SEVERE, "Hex ("  + i + "," + j +
                       //         ") has nbr at (" + (i+k) + ", " + (j+l) + ")");
                    }
                }
                hexMatrix[i][j].setNeighbors(nbrs);

            }
        }
    }

    public void initAllPaths() {
        for (Hexagon[] hs: hexMatrix) {
            for (Hexagon h: hs) {
                h.initPath();
            }
        }
    }

    public void invalidateAllPaths(PointF delta) {
        for (Hexagon[] hs: hexMatrix) {
            for (Hexagon h: hs) {
                h.invalidatePath(delta);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
//        for (Hexagon[] hs: hexMatrix) {
//            for (Hexagon h: hs) {
//                h.draw(canvas);
//            }
//        }
        canvas.drawPath(gridPath, gridPaint);
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
