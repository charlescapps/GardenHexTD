package com.ccapps.android.hextd.gamedata;



/**
 * Created with IntelliJ IDEA.
 * User: monleezy
 * Date: 11/16/12
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gene {

    protected int g;
    /* Genetic breakdown
    First 8-bits - Stamina
    Second 8-bits - Foresight
    Third 8-bits - Threshold
    Fourth 8-bits - Randomness
     */

    public Gene() {
        this.g = (int) (Math.random() * 0xFFFFFFFF);
    }

    public Gene(int g) {
        this.g = g;
    }

    public int getGene() {
        return this.g;
    }

    public int getStamina() {
        return (this.g >> 24) & 0xFF;
    }

    public int getForesight() {
        return (this.g >> 16) & 0xFF;
    }

    public int getThreshold() {
        return (this.g >> 8) & 0xFF;
    }

    public int getRandomness() {
        return this.g & 0xFF;
    }

}
