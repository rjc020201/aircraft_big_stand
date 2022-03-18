package edu.hitsz.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemyShootTool.EnemyGun;

import java.util.LinkedList;
import java.util.List;

public abstract class EnemyAircraft extends AbstractAircraft {
    protected int shootNum=1;
    protected int direction=1;
    protected int power;

    protected EnemyGun gun;

    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }


    public int getDirection(){
        return direction;
    };

    public int getShootNum(){
        return shootNum;
    };

    public int getPower(){
        return power;
    };

    public void setShootNum(int shootNum){
        this.shootNum=shootNum;
    }

    public void setPower(int power){
        this.power=power;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    public void setGun(EnemyGun gun){
        this.gun=gun;
    }

    @Override
    public List<AbstractBullet> shoot() {
        if(this.gun==null){
            System.out.println("there is no gun!!!");
            System.exit(-1);
        }
        return gun.toolShoot(this);
    }

}
