package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.CreepUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 11/27/12
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomCreepGenerator implements CreepGenerator{

    private List<Class<?extends Creep>> creepClasses;
    private List<Class<?extends CreepAlgorithm>> creepAlgorithmClasses;
    private List<Hexagon> sourceHexes;
    private Hexagon goalHex;
    private int speed = 24;
    private int tick = 0;
    private Random random;
    private final int maxCreeps;
    private int numSpawned;

    public RandomCreepGenerator(List<Hexagon> sourceHexes, Hexagon goalHex, int maxCreeps) {
        this.creepClasses = new ArrayList<Class<? extends Creep>>();
        this.creepAlgorithmClasses = new ArrayList<Class<? extends CreepAlgorithm>>();
        this.sourceHexes = sourceHexes;
        this.goalHex = goalHex;
        this.random = new Random();
        this.maxCreeps = maxCreeps;
        this.numSpawned = 0;
    }

    @Override
    public void tick() {
        if (++tick % speed != 0) {
            return;
        };
        if (numSpawned < maxCreeps) {
            numSpawned++;
            int randSource = random.nextInt(sourceHexes.size());
            int randType = random.nextInt(creepClasses.size());
            CreepUtils.addCreep(creepClasses.get(randType), sourceHexes.get(randSource), goalHex, creepAlgorithmClasses.get(randType));
        }
        else if (numSpawned >= maxCreeps) {
            if (HexGrid.getInstance().getCreepsOnGrid().size()<=0) {
                if (Player.getInstance().getLife() > 0) {
                    //win
                }
                else {
                    //lose
                }
            }
        }
    }

    @Override
    public int getSpeed() {
        return speed;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public List<Hexagon> getSourceHexes() {
        return sourceHexes;
    }

    @Override
    public void setSourceHexes(List<Hexagon> sourceHexes) {
        this.sourceHexes = sourceHexes;
    }

    @Override
    public void addCreepToQueue(Creep c) {
    }

    @Override
    public void setCreepClass(Class<? extends Creep> creepClass) {
        creepClasses.add(creepClass);
    }

    @Override
    public void setCreepAlgorithm(Class<? extends CreepAlgorithm> creepAlgorithm) {
        creepAlgorithmClasses.add(creepAlgorithm);
    }
}
