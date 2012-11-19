package com.ccapps.android.hextd.metagame;

import com.ccapps.android.hextd.gamedata.Creep;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106, 963099011
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class BasicGameManager implements GameManager{

    Player player;
    int moneyPerTick;
    int ticksUntilMoney;
    int tick = 0;


    public BasicGameManager(Player player, int moneyPerTick, int ticksUntilMoney) {
        this.player = player;
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

    @Override
    public Player getPlayer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPlayer(Player player) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
//CLC: Original Code End
