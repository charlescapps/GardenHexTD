package com.ccapps.android.hextd;

import android.graphics.PointF;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.ccapps.android.hextd.activities.GameActivity;
import com.ccapps.android.hextd.draw.HexGrid;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void startGame(View v) {

        HexGrid.initHexGrid(new PointF(0.f, 0.f), 10, 20, 40.f);

        Log.i("Test", "starting GameActivity");
    	Intent intent = new Intent(this, GameActivity.class);
    	this.startActivity(intent);
    	
    }

    
}
