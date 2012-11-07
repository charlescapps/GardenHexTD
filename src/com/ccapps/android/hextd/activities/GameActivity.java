package com.ccapps.android.hextd.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.algorithm.RandomWalkAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.*;
import com.ccapps.android.hextd.gamedata.SunflowerTower;
import com.ccapps.android.hextd.gamedata.terrain.BarrierTower;
import com.ccapps.android.hextd.gamedata.terrain.RandomTerrainManager;
import com.ccapps.android.hextd.gamedata.terrain.TerrainManager;
import com.ccapps.android.hextd.metagame.BasicCreepGenerator;
import com.ccapps.android.hextd.metagame.CreepGenerator;
import com.ccapps.android.hextd.views.GameView;
import com.ccapps.android.hextd.views.TowerMenuView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

public class GameActivity extends Activity {

    private GameLogicThread gameLogicThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        HexGrid GRID = HexGrid.getInstance();
        List<Hexagon> sourceHexes = new ArrayList<Hexagon>();

        Hexagon goalHex = GRID.get(GRID.getNumVertical() - 1, 5);
        GRID.setGoalHex(goalHex.getGridPosition().x, goalHex.getGridPosition().y, true);

        sourceHexes.add(GRID.get(0,5));
        CreepGenerator creepGenerator = new BasicCreepGenerator(sourceHexes, goalHex);

        GameView v = (GameView)findViewById(R.id.gameView);
        this.gameLogicThread = new GameLogicThread(v, creepGenerator);
        v.setGameLogicThread(gameLogicThread);

        TerrainManager terrainManager = new RandomTerrainManager(0.1f, BarrierTower.class);
        terrainManager.initTerrain(GRID);

        setupTowerSelectMenu();
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
        GameView.towerMenu = menu;
        menu.init(mThumbIds,mTowerClasses, numPerRow, imageSize, padding);


    }
}
