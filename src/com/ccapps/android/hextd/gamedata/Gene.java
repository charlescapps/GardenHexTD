package com.ccapps.android.hextd.gamedata;



/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

// JL: Original Code Begin
public class Gene {

    protected int genome;
    /* Genome breakdown
    First 8-bits - Stamina
    Second 8-bits - Foresight
    Third 8-bits - Threshold
    Fourth 8-bits - Randomness
    * Stamina - # of steps in addition to admissible heuristic from source to goal creep will take before "giving up,"
    *   and returns to source without having reached the destination. (Range: 0 - 63)
    * Foresight, Threshold - Foresight is the number of steps in predetermined path that creep can "see" and count how
    *   many of those hexes are under attack. If the counted attacked hexes is greater than the Threshold attribute,
    *   creep will reroute path to a safe hex and reapply A* star. (Foresight Range: 0 - 15, Threshold Range: 0-3)
    * Randomness - Chance (range of 0 to 3) + 1 out of 512 of having the predetermined path be thrown out in favor of
        of taking a random, legal step. (Range: 0 - 3)
    */

    // constructor without argument creates random
    public Gene() {
        this.genome = ((int) (Math.random() * 0xFFFFFFFF)) & 0x3F0F0303;
    }

    public Gene(int genome) {
        // make certain that it complies with ranges
        // randomly mutate

        int randbit = 0;

        // randomly mutate, 1/256 chance of it effecting genome
        if ((int)(Math.random() * 128) <= 1) {
            randbit = 1 << (int) (Math.random() * 32);
        }
        this.genome = (genome ^ randbit) & 0x3F0F0303;
    }

    public int getGene() {
        return this.genome;
    }

    public int getStamina() {
        return (this.genome >> 24) & 0x3F;
    }

    public int getForesight() {
        return (this.genome >> 16) & 0x0F;
    }

    public int getThreshold() {
        return (this.genome >> 8) & 0x03;
    }

    public int getRandomness() {
        return this.genome & 0x03;
    }

}
// JL: Original Code End