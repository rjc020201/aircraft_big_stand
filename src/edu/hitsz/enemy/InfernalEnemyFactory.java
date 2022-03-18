package edu.hitsz.enemy;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.enemyShootTool.InfernalEnemyGun;
import edu.hitsz.tool.Observer;

public class InfernalEnemyFactory extends EnemyFactory{
    public InfernalEnemyFactory(int difficult, long startTime) {
        super(difficult, startTime);
    }

    @Override
    public int getDifficultNow() {
        return 0;
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {
        InfernalEnemy infernalEnemy=new InfernalEnemy(
        (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.get(InfernalEnemy.class.getName()).getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                (difficult-1)*5*((Math.random())>0.5? 1:-1),
                10*difficult+difficultNow,
                30*difficult+difficultNow
        );
        infernalEnemy.setPower(1);
        infernalEnemy.setShootNum(1);
        infernalEnemy.setGun(new InfernalEnemyGun());
        observerManager.addObserver(new Observer(infernalEnemy));
        return infernalEnemy;
    }
}
