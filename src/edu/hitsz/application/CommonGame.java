package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemy.Boss;
import edu.hitsz.enemy.EnemyAircraft;
import edu.hitsz.tool.Tool;
import edu.hitsz.tool.ToolAction;

import java.awt.*;
import java.util.List;

public class CommonGame extends Game{


    public CommonGame(int easyNum,boolean musicOpen){
        super(easyNum,musicOpen);
    }

    @Override
    public void enemyCreate() {
        if(score%100>=30 && score%100<=40 && bossExit==0){
            enemyAircrafts.add(bossFactory.createEnemyAircraft());
            this.bgm_boss=MusicPlayer.playMusic("src/music/bgm_boss");
//            bgm_boss.start();
            bossExit=1;
        }else if(enemyAircrafts.size() < enemyMaxNumber){
            if((int)(Math.random()*10)/2==0){
                enemyAircrafts.add(infernalEnemyFactory.createEnemyAircraft());
            }else{
                enemyAircrafts.add(mobEnemyFactory.createEnemyAircraft());
            }
        }
    }

    @Override
    public void shootAction() {
        // TODO 敌机射击
        for(AbstractAircraft enemy:enemyAircrafts){
            List<AbstractBullet> enemyBullet=enemy.shoot();
            observerManager.addObserver(enemyBullet);
            enemyBullets.addAll(enemyBullet);
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
        playMusic("src/music/bullet.wav");
    }

    @Override
    public void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(difficult * 5 + difficultNow);
                bullet.vanish();
            }
        }


        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (!(enemyAircraft instanceof Boss)) {
                            playMusic("src/music/bullet_hit.wav");
                        }else{
                            bossExit = 0;
//                            this.bgm_boss.interrupt();
                            score += 90;
                        }
                        // TODO 获得分数，产生道具补给
                        if ((int) (Math.random() * 10) == 3) {
                            drugs.add(drugFactory.createTool(enemyAircraft));
                        } else if ((int) (Math.random() * 10) == 9) {
                            powders.add(powderFactory.createTool(enemyAircraft));
                        } else if ((int) (Math.random() * 10) == 7) {
                            bombs.add(bombFactory.createTool(enemyAircraft));
                        }
                        score += 10;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 计算我方道具失效
        if(heroAircraft.isTimeUp()){
            heroAircraft.decreasePowder();
        }

//         我方获得道具，道具生效
        ToolCrash(powders, (tool) -> {
            playMusic("src/music/get_supply.wav");
            heroAircraft.increasePowder();
        });

        ToolCrash(drugs, (tool) -> {
            playMusic("src/music/get_supply.wav");
            heroAircraft.increaseHp();
        });

        ToolCrash(bombs, (tool) -> {
            playMusic("src/music/bomb_explosion.wav");
        });
    }

    public void playMusic(String path){
        if(super.isMusicOpen()){
            MusicPlayer.playshortMusic(path);
        }
    }

    private void ToolCrash(List<Tool> tools, ToolAction ta){
        for(Tool tool:tools){
            if(tool.notValid()){
                continue;
            }
            if(heroAircraft.crash(tool)){
                tool.vanish();
                ta.useTool(tool);
            }
        }
    }

    @Override
    public void setBackGround(){
        ImageManager.setBackgroundImage("src/images/bg2.jpg");
    }

    @Override
    public void paintWhich(Graphics g) {
//         先绘制子弹，后绘制飞机
//         这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g,drugs);
        paintImageWithPositionRevised(g,powders);
        paintImageWithPositionRevised(g,bombs);
    }
}
