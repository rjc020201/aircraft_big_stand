package edu.hitsz.enemy;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.enemyShootTool.MobEnemyGun;
import edu.hitsz.tool.Observer;

public class MobEnemyFactory extends EnemyFactory{
    public MobEnemyFactory(int difficult, long startTime) {
        super(difficult, startTime);
    }

    @Override
    public int getDifficultNow() {
        return 0;
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {
        MobEnemy mobEnemy=new MobEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.get(MobEnemy.class.getName()).getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                (difficult-1)*5*((Math.random())>0.5? 1:-1),
                10*difficult+difficultNow,
                30+difficultNow
        );
        mobEnemy.setShootNum(0);
        mobEnemy.setPower(0);
        mobEnemy.setGun(new MobEnemyGun());
        observerManager.addObserver(new Observer(mobEnemy));
        return mobEnemy;
    }
}
