package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.algorithm.GeneAlgorithm;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.AntCreep;
import com.ccapps.android.hextd.gamedata.Gene;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public class GeneticCreepGenerator extends BasicCreepGenerator {

    Queue<AntCreep> creepQueue = new LinkedList<AntCreep>();
    int startCount;


    public GeneticCreepGenerator(List<Hexagon> sourceHexes, Hexagon goalHex) {
        super(sourceHexes, goalHex);
        this.creepAlgorithm = GeneAlgorithm.class;
    }

    // Genetic algorithm
    // reproduction operation
    public Gene crossover(Gene parent1, Gene parent2) {
        Gene child;
        int childGenome,
                stam,
                fore,
                thresh,
                rand;

        // crossover operation takes mean of two parent attributes
        stam = (parent1.getStamina() + parent2.getStamina()) / 2;
        fore = (parent1.getForesight() + parent2.getForesight()) / 2;
        thresh = (parent1.getThreshold() + parent2.getThreshold()) / 2;
        rand = (parent1.getRandomness() + parent2.getRandomness()) / 2;

        childGenome = (stam << 24) | (fore << 16) | (thresh << 8) | rand;
        child = new Gene(childGenome);
        // mutation is performed in constructor

        return child;
    }
}
