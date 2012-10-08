package com.ccapps.android.hextd.activities;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.TowerDrawable;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.views.GameView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity {

    private GameLogicThread gameLogicThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GameView v = (GameView)findViewById(R.id.gameView);

        this.gameLogicThread = new GameLogicThread(v);

        v.setGameLogicThread(gameLogicThread);

        HexGrid grid = HexGrid.getInstance();
        int numVertical = grid.getNumVertical();
        int numHorizontal = grid.getNumHorizontal();

        grid.setGoalHex(numVertical-1, numHorizontal/2, true);

        /**
         * Set a tower at (5,5)
         */
        HexGrid hexGrid = HexGrid.getInstance();

        Tower basicTower = new BasicTower(hexGrid.get(5,5));
        hexGrid.setTower(5, 5, basicTower);

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
        gameLogicThread.postSuspendMe();
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   HexGrid.getInstance().reset();
        GameView v = (GameView)findViewById(R.id.gameView);
        v.startDrawing();
        gameLogicThread.unSuspendMe();

    }
}
