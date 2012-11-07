package com.ccapps.android.hextd.gamedata.terrain;

import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.gamedata.TowerUtils;

import java.util.List;
import java.util.Random;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public class RandomTerrainManager implements TerrainManager{

    private float density;
    private Random random;
    private Class<? extends Tower> towerClass;
    private List<Hexagon> sourceHexes;

    public RandomTerrainManager(float density, Class<? extends Tower> towerClass) {
        this.density = density;
        this.random = new Random();
        this.towerClass = towerClass;
        this.sourceHexes = sourceHexes;
    }
    @Override
    public void initTerrain(HexGrid grid) {
        for (int i = 0; i < grid.getNumVertical(); i++) {
            for (int j = 0; j < grid.getNumHorizontal(); j++) {
                Hexagon hex = grid.get(i,j);
                float randomFloat = random.nextFloat();
                if (randomFloat < density && hex.getTower() == null && hex.getMyState() == Hexagon.STATE.NORMAL) {
                    TowerUtils.addTower(towerClass, i, j);
                }
            }
        }
    }
}
