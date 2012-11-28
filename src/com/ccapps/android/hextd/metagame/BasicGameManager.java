package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.gamedata.Creep;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class BasicGameManager implements GameManager{

    Player player;
    int moneyPerTick;
    int ticksUntilMoney;
    int tick = 0;


    public BasicGameManager(int moneyPerTick, int ticksUntilMoney) {
        this.player = Player.getInstance();
        this.moneyPerTick = moneyPerTick;
        this.ticksUntilMoney = ticksUntilMoney;
    }

    @Override
    public void tick() {
        ++tick;
        if (tick % ticksUntilMoney == 0) {
            player.addMonies(moneyPerTick);
        }

    }

    @Override
    public void creepHitsGoal(Creep creep) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
//CLC: Original Code End
