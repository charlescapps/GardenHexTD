package com.ccapps.android.hextd;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import com.ccapps.android.hextd.activities.GameActivity;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.draw.TowerDrawable;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.Tower;

public class MainActivity extends Activity {

    public static final int NUM_VERTICAL_HEXES = 16;                  //test values for now...
    public static final int NUM_HORIZONTAL_HEXES = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());

        float gridWidth =  (float)(screenSize.x - 2*HexGrid.MARGIN.x);

        /**
         * Calculate the side of a hexagon, a and the height from the center to top, h
         */
        float a = gridWidth * 1.f / ( (3.f/2.f) * (float)NUM_HORIZONTAL_HEXES + 1.f/2.f);
        float h = a*Hexagon.sqrt3/2.f;

        PointF margin = new PointF(5.f, 5.f + (float)R.integer.drawer_menu_size);
        HexGrid.initHexGrid(
                new PointF(a/2.f + HexGrid.MARGIN.x, h + HexGrid.MARGIN.y),
                NUM_HORIZONTAL_HEXES,
                NUM_VERTICAL_HEXES,
                a,
                screenSize,
                margin);

        /**
         * Set a tower at (5,5)
         */
        HexGrid hexGrid = HexGrid.getInstance();

        Tower basicTower = new BasicTower(hexGrid.get(5,5));
        hexGrid.setTower(5, 5, basicTower);
        TowerDrawable towerDrawable = new TowerDrawable(basicTower,
                BitmapFactory.decodeResource(getResources(), R.drawable.tower));
        hexGrid.get(5, 5).getTower().setTowerDrawable(towerDrawable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void startGame(View v) {

        Log.i("Test", "starting GameActivity");
    	Intent intent = new Intent(this, GameActivity.class);
    	this.startActivity(intent);
    	
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    
}
