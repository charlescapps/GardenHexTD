package com.ccapps.android.hextd.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.algorithm.RandomWalkAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.gamedata.*;
import com.ccapps.android.hextd.gamedata.SunflowerTower;
import com.ccapps.android.hextd.views.GameView;
import com.ccapps.android.hextd.views.TowerMenuView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends Activity {

    private GameLogicThread gameLogicThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupTowersAndGoals();
        setupTowerSelectMenu();
        setupCreeps();

        GameView v = (GameView)findViewById(R.id.gameView);
        this.gameLogicThread = new GameLogicThread(v);
        v.setGameLogicThread(gameLogicThread);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    protected void  onStart() {
       super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
       // HexGrid.getInstance().reset();
        GameView v = (GameView)findViewById(R.id.gameView);
        v.startDrawing();
        gameLogicThread.unSuspendMe();
    }

    private void setupTowersAndGoals() {

        HexGrid grid = HexGrid.getInstance();
        int numVertical = grid.getNumVertical();
        int numHorizontal = grid.getNumHorizontal();

        grid.setGoalHex(numVertical-1, numHorizontal/2, true);

    }

    private void setupCreeps() {
        HexGrid GRID = HexGrid.getInstance();
        CreepUtils.addCreep(AntCreep.class, 0, 0, GRID.getGoalHexes().get(0), RandomWalkAlgorithm.class);
    }

    private void setupTowerSelectMenu() {

        List<Integer> mThumbIds = Arrays.asList(new Integer[]{
                R.drawable.sunflower_tower,
                R.drawable.carnivorous_tower,
                R.drawable.eggplant_tower,
                R.drawable.rose_tower
        });

        List<Class<? extends Tower>> mTowerClasses = new ArrayList<Class<? extends Tower>>();
        mTowerClasses.add(SunflowerTower.class);
        mTowerClasses.add(CarnivorousTower.class);
        mTowerClasses.add(EggplantTower.class);
        mTowerClasses.add(RoseTower.class);

        int numPerRow = 2;
        int imageSize = 65;
        int padding = 5;

        TowerMenuView menu = (TowerMenuView)findViewById(R.id.towerMenuTable);
        menu.init(mThumbIds,mTowerClasses, numPerRow, imageSize, padding);


    }
}
