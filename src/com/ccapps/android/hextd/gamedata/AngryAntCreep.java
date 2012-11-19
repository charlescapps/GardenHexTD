package com.ccapps.android.hextd.gamedata;

import com.ccapps.android.hextd.algorithm.CreepAlgorithm;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin

public class AngryAntCreep extends AntCreep{
    public static enum STATE {NEUTRAL, EVADE, ANGRY};
    private STATE state;
    private int MAX_SPEED;
    private int MAX_HITPOINTS;
    private int prevHitpoints;
    private int evadeTime;
    private final int EVADE_DURATION = 10;

    public AngryAntCreep(Hexagon hex, Hexagon goalHex, CreepAlgorithm algorithm){
        super(hex, goalHex, algorithm);
        state = STATE.NEUTRAL;
        MAX_SPEED = speed;
        MAX_HITPOINTS = prevHitpoints = hitpoints;
        evadeTime = 0;
    }

    public STATE getState() {
        return state;
    }


    @Override
    public void move() {
        if (++tick % speed == 0 && hitpoints > 0) {
            evaluateRoute();
            if (path == null || path.size() <= 0) {
                return;
            }
            hex.removeCreep(this);
            hex = path.remove(0);
            hex.setCreep(this);
            creepDrawable.updateLocation();

            if (state != STATE.ANGRY && hitpoints < MAX_HITPOINTS / 2) {
                state = STATE.ANGRY;
                speed = MAX_SPEED / 2;
            }
            else {
                if (state == STATE.EVADE) {
                    ++evadeTime;
                    if (evadeTime >= EVADE_DURATION) {
                        state = STATE.NEUTRAL;
                    }
                }
                else if (state != STATE.EVADE && prevHitpoints > hitpoints) {
                    state = STATE.EVADE;
                    evadeTime = 0;
                }
            }

            if (hex == goalHex) {
                if (forageState == FORAGE_STATE.FORAGE) {
                    goalHex = sourceHex;
                    sourceHex = hex;
                    path = null;
                    forageState = FORAGE_STATE.RETURN;
                }
                else {
                    forageState = FORAGE_STATE.BACK_TO_HIVE;
                    HexGrid.getInstance().removeCreep(this);
                    hex.removeCreep(this);
                }
            }
            prevHitpoints = hitpoints;

        }
    }
}
//CLC: Original Code End
