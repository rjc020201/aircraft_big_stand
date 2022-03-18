package edu.hitsz.enemyShootTool;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemy.EnemyAircraft;

import java.util.LinkedList;

public interface EnemyGun {
    LinkedList<AbstractBullet> toolShoot(EnemyAircraft fly);
}
