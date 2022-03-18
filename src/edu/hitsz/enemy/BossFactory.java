package edu.hitsz.enemy;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.enemyShootTool.BossGun;

public class BossFactory extends EnemyFactory{
    private HeroAircraft heroAircraft;
    public BossFactory(int difficult, long startTime, HeroAircraft heroAircraft) {
        super(difficult, startTime);
        this.heroAircraft=heroAircraft;
    }

    @Override
    public int getDifficultNow() {
        return 0;
    }

    @Override
    public EnemyAircraft createEnemyAircraft() {
        Boss boss=new Boss(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.get(Boss.class.getName()).getWidth()))*1,
                30,
                (difficult-1)*5*((Math.random())>0.5? 1:-1),
                10,
                300*difficult,
                this.heroAircraft);
        boss.setPower(30);
        boss.setShootNum(3);
        boss.setGun(new BossGun());
        return boss;
    }
}
