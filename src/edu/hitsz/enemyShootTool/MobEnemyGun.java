package edu.hitsz.enemyShootTool;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemy.EnemyAircraft;

import java.util.LinkedList;

public class MobEnemyGun implements EnemyGun {
    @Override
    public LinkedList<AbstractBullet> toolShoot(EnemyAircraft mobEnemyGun) {
        return new LinkedList<>();
    }
}
