package com.ccapps.android.hextd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.ccapps.android.hextd.activities.GameActivity;

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
    	
    	Log.i("Test", "starting GameActivity");
    	Intent intent = new Intent(this, GameActivity.class);
    	this.startActivity(intent);
    	
    }

    
}
