package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.algorithm.GeneAlgorithm;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.AntCreep;
import com.ccapps.android.hextd.gamedata.Creep;
import com.ccapps.android.hextd.gamedata.Gene;

import java.util.*;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

// JL: Original Code Begin
public class GeneticCreepGenerator extends BasicCreepGenerator {

    Queue<Creep> creepQueue;
    Gene queen;
    List<List<Hexagon>> successfulPaths;
    int startCount;

    public GeneticCreepGenerator(List<Hexagon> sourceHexes, Hexagon goalHex, int startCount) {
        super(sourceHexes, goalHex);
        //this.creepAlgorithm = GeneAlgorithm.class;
        this.queen = new Gene();
        this.creepQueue = new LinkedList<Creep>();
        List<List<Hexagon>> successfulPaths = new ArrayList<List<Hexagon>>(4);
        this.startCount = startCount;
    }

    // Constructor for supplied creep list
    public GeneticCreepGenerator(List<Hexagon> sourceHexes, Hexagon goalHex, List<Creep> creepList) {
        this(sourceHexes, goalHex, 0);
        creepQueue.addAll(creepList);
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
        // mutation is performed in constructor of gene with genome (int) argument

        return child;
    }

    // Creep returns to nest
    // If creep returns from successful journey (creep.getGoalMet()),
    // creep gets opportunity to propogate its species and mate with queen.
    // Otherwise, creep merely enqueues the creep list.
    // When mating, successful creep & queen are parent combination. Successful creep
    // and new child enqueue the creep list and await next deployment
    // Successful creep also "reports" it's successful path
    public void returnCreep(Creep creep) {
        creep.setGoalHex(null);     // off hexgrid
        if (creep.getGoalMet() == true) {
            Gene childGene = this.crossover(creep.getAttr(), this.queen);
            CreepAlgorithm algo = new GeneAlgorithm();
            Creep child = new AntCreep(this.sourceHexes.get(0), this.goalHex, algo);
            child.setAttr(childGene);
            this.creepQueue.add(child);
            this.successfulPaths.add(creep.getPrevPath());
        }

        this.creepQueue.add(creep);
    }

    // dequeue creep from list to be used on board
    public Creep deployCreep() {
        if (this.creepQueue == null || this.creepQueue.size() == 0) {
            if (this.startCount == 0)
                return null;
            else {
                CreepAlgorithm algo = new GeneAlgorithm();
                return new AntCreep(this.sourceHexes.get(0), this.goalHex, algo);
            }
        }
        Creep creep = this.creepQueue.remove();
        if (this.successfulPaths != null && this.successfulPaths.size() > 0) {
            creep.setPath(this.getSuccessfulPath());
        }
        return creep;
    }

    // return a path in descending order of possibility according to how recent
    // the successful path was reported
    // each successful path is as likely to be chosen as the sum of the possibilities
    // of all the paths that came before it, max size = 10
    // example:
    // 2 successful paths: 1 - 50%, 2 - 50%
    // 3 successful paths: 1 - 50%, 2 - 25%, 3 - 25%
    // 4 successful paths: 1 - 50%, 2 - 25%, 3 - 12.5%, 4 - 12.5%
    // incorporate chance of null path
    private List<Hexagon> getSuccessfulPath() {
        int size = this.successfulPaths.size();
        double rand = Math.random();

        switch(size) {
            case 1:
                return this.successfulPaths.get(0);
            case 2:
                if (rand >= 0.5) {
                    return this.successfulPaths.get(1);
                }
                return this.successfulPaths.get(0);
            case 3:
                if (rand >= 0.75) {
                    return this.successfulPaths.get(2);
                }
                else if (rand >= 0.5) {
                    return this.successfulPaths.get(1);
                }
                return this.successfulPaths.get(0);
            case 4:
                if (rand >= 0.875) {
                    return this.successfulPaths.get(3);
                }
                else if (rand >= 0.75) {
                    return this.successfulPaths.get(2);
                }
                else if (rand >= 0.5) {
                    return this.successfulPaths.get(1);
                }
                return this.successfulPaths.get(0);
            default:
                return null;
        }
    }
}
// JL: Original Code End