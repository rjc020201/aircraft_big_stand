package edu.hitsz.bullet;

import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;

public interface HeroBulletCreater{
    List<AbstractBullet> createBullet(HeroAircraft heroAircraft);
}
