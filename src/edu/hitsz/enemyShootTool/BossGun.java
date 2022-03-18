package edu.hitsz.enemyShootTool;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.enemy.Boss;
import edu.hitsz.enemy.EnemyAircraft;

import java.util.LinkedList;
import java.util.List;

public class BossGun implements EnemyGun{
    @Override
    public LinkedList<AbstractBullet> toolShoot(EnemyAircraft boss) {
        LinkedList<AbstractBullet> res = new LinkedList<>();
        int x = boss.getLocationX();
        int y = boss.getLocationY() + boss.getDirection()*2;
        int speedY = boss.getSpeedY() + boss.getDirection()*5;
        AbstractBullet abstractBullet;
        int shootNum=boss.getShootNum();
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, 5*(i*2-shootNum+1), speedY, boss.getPower());
            res.add(abstractBullet);
        }
        return res;
    }
}
