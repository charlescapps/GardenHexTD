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
import com.ccapps.android.hextd.draw.TowerDrawable;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.Tower;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());
        HexGrid.initHexGrid(new PointF(0.f, 0.f), 10, 20, 30.f, screenSize);
        HexGrid hexGrid = HexGrid.getInstance();
        Tower basicTower = new BasicTower(hexGrid.get(1,0));
        hexGrid.setTower(1, 0, basicTower);
        TowerDrawable towerDrawable = new TowerDrawable(basicTower,
                BitmapFactory.decodeResource(getResources(), R.drawable.tower));
        hexGrid.get(1, 0).getTower().setTowerDrawable(towerDrawable);
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

    
}
