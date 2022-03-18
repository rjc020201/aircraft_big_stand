package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.*;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    /**
     * 子弹策略管理器
     */
    private BulletProduct bulletProduct=new BulletProduct(new HeroBulletOne());

    /**
     * 子弹的计时管理器
     */
    private TimeCount timeCount;

    /**
     * 使用单例模式重构英雄机代码
     */
    private static HeroAircraft heroAircraft=new HeroAircraft(
            Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.get(HeroAircraft.class.getName()).getHeight() ,
            0, 0, 100);

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public static HeroAircraft getInstance(){
        return heroAircraft;
    }

    public static void reSetHeroAricraft(int locationX,int locationY,int speedX,int speedY,int hp,long startTime){
        heroAircraft.locationX=locationX;
        heroAircraft.locationY=locationY;
        heroAircraft.speedX=speedX;
        heroAircraft.speedY=speedY;
        heroAircraft.hp=hp;
        heroAircraft.timeCount=new TimeCount(startTime);
    }

    public int getDirection(){
        return this.direction;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void decreaseHp(int number){
        this.hp=this.hp-number;
    }

    public void decreaseHp(){
        decreaseHp(1);
    }

    @Override
    public List<AbstractBullet> shoot() {
        return bulletProduct.createBullet(this);
    }

    public void setShoot(int heroBulletNumber){
        bulletProduct.setBulletCreater(heroBulletNumber);
    }

    public void increaseHp(){
        increaseHp(1);
    }

    public void increaseHp(int number) {
        this.hp=this.hp+number;
    }

    public void increasePowder() {
        int bulletProductPower= bulletProduct.getPower();
        if(bulletProductPower<3){
            setShoot(bulletProductPower+1);
        }
        timeCount.restartTime();
    }

    public boolean isTimeUp(){
        return timeCount.isTimeUp();
    }

    public void decreasePowder(){
        int bulletProductPower= bulletProduct.getPower();
        if(bulletProductPower>1){
            bulletProduct.setBulletCreater(1);
        }
    }
}

//没有做long的数值异常检查
class TimeCount{
    private long time;
    public TimeCount(long startTime){
        this.time=startTime;
    }

    public boolean isTimeUp(){
        if(time<System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    public void restartTime(){
        time=System.currentTimeMillis()+5000;
    }
}