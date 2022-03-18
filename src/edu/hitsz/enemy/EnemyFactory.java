package edu.hitsz.enemy;

import edu.hitsz.application.Game;
import edu.hitsz.tool.ObserverManager;

public abstract class EnemyFactory {
    protected int difficult;
    protected int difficultNow;
    protected long startTime;
    protected ObserverManager observerManager= Game.observerManager;
    public EnemyFactory(int difficult,long startTime){
        this.difficult=difficult;
        this.startTime=startTime;
    }
    public abstract int getDifficultNow();
    public abstract EnemyAircraft createEnemyAircraft();
}
