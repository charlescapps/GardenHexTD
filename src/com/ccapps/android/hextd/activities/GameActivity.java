package com.ccapps.android.hextd.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.ccapps.android.hextd.R;

public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
