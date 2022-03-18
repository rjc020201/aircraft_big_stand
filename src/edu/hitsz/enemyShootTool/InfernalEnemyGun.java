package edu.hitsz.enemyShootTool;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.enemy.EnemyAircraft;

import java.util.LinkedList;



public class InfernalEnemyGun implements EnemyGun{
    @Override
    public LinkedList<AbstractBullet> toolShoot(EnemyAircraft infernalEnemy) {
        LinkedList<AbstractBullet> res = new LinkedList<>();
        int x = infernalEnemy.getLocationX();
        int y = infernalEnemy.getLocationY() + infernalEnemy.getDirection()*2;
        int speedY = infernalEnemy.getSpeedY() + infernalEnemy.getDirection()*5;
        int speedX=0;
        AbstractBullet abstractBullet;
        int shootNum=infernalEnemy.getShootNum();
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, infernalEnemy.getPower());
            res.add(abstractBullet);
        }
        return res;
    }
}
