package edu.hitsz.bullet;

import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;

public class BulletProduct {
    private HeroBulletCreater hbc;
    private int power=1;
    public BulletProduct(HeroBulletCreater hbc){
        this.hbc=hbc;
    }
    public void setBulletCreater(int heroBulletNumber){
        this.power=heroBulletNumber;
        switch (heroBulletNumber){
            case 1:
                this.hbc=new HeroBulletOne();
                break;
            case 2:
                this.hbc=new HeroBulletTwo();
                break;
            case 3:
                this.hbc=new HeroBulletThree();
                break;
            default:;
        }
    }
    public List<AbstractBullet> createBullet(HeroAircraft heroAircraft){
        return hbc.createBullet(heroAircraft);
    }
    public int getPower(){
        return power;
    }
}
