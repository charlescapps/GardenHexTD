package com.ccapps.android.hextd.draw;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.StaticData;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.views.GameView;
import com.ccapps.android.hextd.views.TowerMenuView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

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
    public static float Y_MIN = -5.f;
    public static float Y_MAX;
    public static PointF MARGIN = new PointF(5.f, 25.f); //pixels
    public static PointF GLOBAL_OFFSET = new PointF(0.f, 0.f);


    /**
     * Must be called before getting an instance...
     *
     * @param topLeft
     * @param numHorizontal
     * @param numVertical
     */
    public static void initHexGrid(PointF topLeft, int numHorizontal, int numVertical, float sideLength, Point screenSize, PointF margin ) {

        Hexagon.setGlobalSideLength(sideLength);
        HexGrid.GRID = new HexGrid(numHorizontal, numVertical, screenSize, topLeft);
        shiftTopLeft(topLeft);
    }


    public static void shiftTopLeft(PointF delta) {

        float newX = HexGrid.GLOBAL_OFFSET.x + delta.x;

        float newY = HexGrid.GLOBAL_OFFSET.y + delta.y;

        if ( -newY < Y_MIN || -newY > Y_MAX) {
            return;
        }
        HexGrid.GLOBAL_OFFSET.x = newX;
        HexGrid.GLOBAL_OFFSET.y = newY;

        GRID.gridPath.offset(delta.x, delta.y);

        synchronized (GRID.towersOnGrid) {
            //Shift the path of the hexes every tower is attacking
            for (Tower t: GRID.towersOnGrid) {
                 t.invalidatePaths(delta);

            }
        }

        synchronized (GRID.goalHexes) {
            for (Hexagon h: GRID.goalHexes) {
                h.invalidatePath(delta);
            }
        }

        synchronized (GRID.towersOnGrid) {
            for (Tower t: GRID.towersOnGrid) {
                t.clearWasInvalidated();
            }
        }

        synchronized (GRID.goalHexes) {
            for (Hexagon h: GRID.goalHexes) {
                h.clearWasInvalidated();
            }
        }

        if (GRID.selectedHexagon != null) {
            GRID.selectedHexagon.invalidatePath(delta);
        }

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

    private List<Tower> towersOnGrid;
    private List<Hexagon> goalHexes;
    private List<Creep> creepsOnGrid;
    private Hexagon selectedHexagon;

    public final float gridHeight;
    public final float gridWidth;
    /**
     * A hex grid is laid out basically like a square grid w/ more connections.
     * Will create documentation eventually w/ pretty diagrams.
     */
    private Hexagon[][] hexMatrix;

    /**
     * @param numHorizontal - the number of hexagons left-to-right
     * @param numVertical - the number of hexagons top-to-bottom
     */
    private HexGrid(int numHorizontal, int numVertical, Point screenSize, PointF topLeft) {

        this.topLeft = topLeft;
        this.numHorizontal = numHorizontal;
        this.numVertical = numVertical;

        this.gridPath = new Path();
        this.towersOnGrid = Collections.synchronizedList(new ArrayList<Tower>());
        this.creepsOnGrid = Collections.synchronizedList(new ArrayList<Creep>());
        this.goalHexes = Collections.synchronizedList(new ArrayList<Hexagon>());
        this.gridPaint = new Paint();
        this.gridPaint.setColor(Color.GREEN);
        this.gridPaint.setAlpha(128);
        this.gridPaint.setStrokeWidth(1);
        this.gridPaint.setStyle(Paint.Style.STROKE);
        
        this.hexMatrix = new Hexagon[numVertical][numHorizontal];  //rows / columns starting from top-left (math matrix style)

        float a = Hexagon.getGlobalSideLength();
        float h = a*Hexagon.sqrt3/2.f;

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
                hexMatrix[j][i] = new Hexagon(new PointF(hOffset, vOffset),
                        new Point(j, i));
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

        this.gridHeight = (float)numVertical*2.f*h + h ;
        this.gridWidth = (float)screenSize.x - 2.f*MARGIN.x;

        Y_MIN = -topLeft.y - (float)MARGIN.y;
        Y_MAX = gridHeight + HexGrid.MARGIN.y*3.f + 2.f*h - (float)screenSize.y;
    }

    /************************DRAWING RELATED**********************************/

    public Hexagon getHexFromCoords(float x, float y) {
        if (x - GLOBAL_OFFSET.x + topLeft.x < 0 || y - GLOBAL_OFFSET.y + topLeft.y < 0 ) {
            return null;
        }

        if (x - GLOBAL_OFFSET.x - gridWidth > 0 || y - GLOBAL_OFFSET.y - gridHeight > 0) {
            return null;
        }

        float intoGridByX = x - GLOBAL_OFFSET.x;
        float intoGridByY = y - GLOBAL_OFFSET.y;

        float h = Hexagon.height;
        float a = Hexagon.getGlobalSideLength();
        int approxRow = (int) (intoGridByY / (2.f*h));
        int approxCol = (int) (intoGridByX / (3.f*a/2.f));

        for (int i = approxRow - 1; i <= approxRow + 1; i++) {
            for (int j = approxCol - 1; j <= approxCol + 1; j++) {
                if (i < 0 || j < 0 || i >= numVertical || j >= numHorizontal) {
                    continue;
                }
                Hexagon hex = get(i,j);
                PointF center = hex.getCenter();
                float xDiff = center.x - intoGridByX;
                float yDiff = center.y - intoGridByY;
                if (Math.sqrt(xDiff*xDiff + yDiff*yDiff  ) < a)  {
                    return hex;
                }
            }
        }

        return null;

    }

    public void setSelectedHexagon(Hexagon h) {
        this.selectedHexagon = h;
        this.selectedHexagon.initPath();
    }

    public void clearSelectedHexagon() {
        if (selectedHexagon != null) {
            selectedHexagon.setStateToDefault();
            selectedHexagon = null;
        }
    }

    public static void reset() {
        GLOBAL_OFFSET = new PointF(0.f, 0.f);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(gridPath, gridPaint);
        synchronized (towersOnGrid) {
            for (Tower t: towersOnGrid) {
                t.draw(canvas);
            }
        }
        synchronized (goalHexes) {
            for (Hexagon h: goalHexes) {
                h.draw(canvas);
            }
        }
        synchronized (creepsOnGrid) {
            for (Creep c: creepsOnGrid) {
                c.draw(canvas);
            }
        }
        if (selectedHexagon != null) {
            selectedHexagon.draw(canvas);
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

    /***********************SETTERS / GETTERS*******************************/
    public int getNumVertical() {
        return numVertical;
    }

    public int getNumHorizontal() {
        return numHorizontal;
    }

    public List<Hexagon> getGoalHexes() {
        return goalHexes;
    }

    public Hexagon get(int r, int c) {
        if (r < 0 || c < 0 || r >= numVertical || c >= numHorizontal) {
            return null;
        }
        return hexMatrix[r][c];
    }

    public Hexagon get(Point pos) {
        int r = pos.x; int c = pos.y;
        if (r < 0 || c < 0 || r >= numVertical || c >= numHorizontal) {
            return null;
        }
        return hexMatrix[r][c];
    }

    public void setTower(int r, int c, Tower tower) {
        hexMatrix[r][c].setTower(tower);
        synchronized (towersOnGrid) {
            towersOnGrid.add(tower);
        }
        tower.initPaths();
    }

    public void setCreep(int r, int c, Creep creep) {
        if (hexMatrix[r][c].getTower() != null) {
            StaticData.l.log(Level.SEVERE, "Attempt to place creep on hex with a tower! (" + r + ", " + c + ")");
            return;
        }
        hexMatrix[r][c].setCreep(creep);
        synchronized (creepsOnGrid) {
            creepsOnGrid.add(creep);
        }
        creep.evaluateRoute();
    }

    public List<Tower> getTowersOnGrid() {
        return towersOnGrid;
    }

    public List<Creep> getCreepsOnGrid() {
        return creepsOnGrid;
    }

    /*********************GAME LOGIC RELATED*******************************/

    public void setGoalHex(int r, int c, boolean isGoal) {
        hexMatrix[r][c].setState(Hexagon.STATE.GOAL);
        synchronized (goalHexes) {
            goalHexes.add(hexMatrix[r][c]);
        }
        hexMatrix[r][c].initPath();
    }
}
