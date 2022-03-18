package edu.hitsz.enemy;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemyShootTool.EnemyGun;

import java.util.List;

public class Boss extends EnemyAircraft{
    private HeroAircraft hero;
    public Boss(int locationX, int locationY, int speedX, int speedY, int hp,HeroAircraft hero) {
        super(locationX, locationY, speedX, speedY, hp);
        this.hero=hero;
    }

    public int getSpeedX(){
        return this.speedX;
    }

    @Override
    public void forward() {
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX;
        }

        if(hero.getLocationX()==this.getLocationX()){

        }else if(hero.getLocationX()>this.getLocationX()) {
            this.locationX = this.getLocationX() + this.getSpeedX();
        }else{
            this.locationX = this.getLocationX() - this.getSpeedX();
        }
    }
}
