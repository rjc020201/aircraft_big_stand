package edu.hitsz.bullet;

import edu.hitsz.aircraft.HeroAircraft;

import java.util.ArrayList;
import java.util.List;

public class HeroBulletThree implements HeroBulletCreater{
    @Override
    public List<AbstractBullet> createBullet(HeroAircraft heroAircraft) {
        List<AbstractBullet> res=new ArrayList<>();
        int x = heroAircraft.getLocationX();
        int y = heroAircraft.getLocationY() + heroAircraft.getDirection()*2;
        int speedX = 1;
        int speedY = heroAircraft.getSpeedY() + heroAircraft.getDirection()*5;
        AbstractBullet abstractBullet;
        int shootNum=3;
        int power=90;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, 0, speedY, power);
            res.add(abstractBullet);
        }
        return res;
    }
}
