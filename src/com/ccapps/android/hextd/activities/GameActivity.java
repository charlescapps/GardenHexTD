package com.ccapps.android.hextd.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.views.GameView;

public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameView v = (GameView)findViewById(R.id.gameView);
        v.stopDrawing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameView v = (GameView)findViewById(R.id.gameView);
        v.startDrawing();
    }
}
