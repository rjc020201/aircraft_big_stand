package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.enemy.*;
import edu.hitsz.tool.ObserverManager;
import edu.hitsz.tool.Tool;
import edu.hitsz.toolFactory.BombFactory;
import edu.hitsz.toolFactory.DrugFactory;
import edu.hitsz.toolFactory.PowderFactory;
import edu.hitsz.toolFactory.ToolFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<EnemyAircraft> enemyAircrafts;
    protected final List<AbstractBullet> heroBullets;
    protected final List<AbstractBullet> enemyBullets;
    protected final List<Tool> drugs;
    protected final List<Tool> powders;
    protected final List<Tool> bombs;

    /**
     * 敌机工厂
     */
    protected EnemyFactory mobEnemyFactory;
    protected EnemyFactory infernalEnemyFactory;
    protected EnemyFactory bossFactory;
    protected ToolFactory drugFactory;
    protected ToolFactory powderFactory;
    protected ToolFactory bombFactory;

    public static ObserverManager observerManager=new ObserverManager();

    protected int enemyMaxNumber = 5;

    protected boolean gameOverFlag = false;
    protected int score = 0;
    protected int time = 0;
    protected int bossExit=0;

    protected boolean musicOpen;

    protected int difficult;

    protected long startTime;

    protected Thread bgm;
    protected Thread bgm_boss;
//    protected long endTime;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime= 0;
    protected int difficultNow=0;

    public Game(int difficult, boolean musicOpen){
        HeroAircraft.reSetHeroAricraft(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.get(HeroAircraft.class.getName()).getHeight() ,
                0, 0, 100,System.currentTimeMillis());
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        drugs=new LinkedList<>();
        powders=new LinkedList<>();
        bombs=new LinkedList<>();

        startTime=System.currentTimeMillis();

        Boss boss=null;

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
        this.difficult=difficult;
        this.musicOpen=musicOpen;
        if(musicOpen){

        }
        this.mobEnemyFactory=new MobEnemyFactory(difficult,System.currentTimeMillis());
        this.infernalEnemyFactory=new InfernalEnemyFactory(difficult,System.currentTimeMillis());
        this.bossFactory=new BossFactory(difficult,System.currentTimeMillis(),this.heroAircraft);
        this.powderFactory=new PowderFactory(difficult);
        this.drugFactory=new DrugFactory(difficult);
        this.bombFactory=new BombFactory(difficult);
    }




    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        //音乐
        if(musicOpen){
            bgm=MusicPlayer.playMusic("src/music/bgm.wav");
        }

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                // 新敌机产生
                enemyCreate();

                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            toolsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            gameOver();
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    private final void gameOver(){
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            gameOverFlag = true;
            System.out.println("Game Over!");
            if(musicOpen){
                MusicPlayer.playshortMusic("src/music/game_over.wav");
//                bgm.interrupt();
            }
            JFrameMain.getInstance().changeNextJPanel(""+score,getGameAllTime(System.currentTimeMillis()-startTime),difficult);
        }
    }

    public boolean isMusicOpen(){
        return musicOpen;
    }

    public abstract void enemyCreate();
    //****************************
    //     负责处理记录时间的方法
    //****************************
    private final String getGameAllTime(long l) {
        StringBuffer ans=new StringBuffer();
        if(l<0){
            System.out.println("sorry this is a wrong gametime");
            System.exit(-1);
        }
        long left;
        ans.append(getTimeAddTag(l,24*60*60*1000,"d"));
        left=getTimeTagLeft(l,24*60*60*1000);
        ans.append(getTimeAddTag(left,60*60*1000,"h"));
        left=getTimeTagLeft(left,60*60*1000);
        ans.append(getTimeAddTag(left,60*1000,"min"));
        left=getTimeTagLeft(left,60*1000);
        ans.append(getTimeAddTag(left,1000,"s"));
        return ans.toString();
    }

    private final String getTimeAddTag(long l,long div,String tag){
        StringBuffer ans=new StringBuffer("");
        long shang=l/div;
        if(shang>0){
            ans.append(shang+tag);
        }
        return ans.toString();
    }

    private final long getTimeTagLeft(long l,long div){
        return l%div;
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public abstract void shootAction();

    //下面是移动动作，先不打算放到模板里面，因为没有太多好扩展的
    private void bulletsMoveAction() {
        forward(heroBullets);
        forward(enemyBullets);
    }

    private void aircraftsMoveAction() {
        forward(enemyAircrafts);
    }

    private void toolsMoveAction(){
        forward(drugs);
        forward(bombs);
        forward(powders);
    }

    private void forward(List lists){
        for(Object element:lists){
            ((AbstractFlyingObject)element).forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    public abstract void crashCheckAction();



    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        drugs.removeIf(AbstractFlyingObject::notValid);
        powders.removeIf(AbstractFlyingObject::notValid);
        bombs.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param
     */

    public abstract void setBackGround();
    public abstract void paintWhich(Graphics g);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setBackGround();

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        paintWhich(g);


        g.drawImage(ImageManager.get(HeroAircraft.class.getName()), heroAircraft.getLocationX() - ImageManager.get(HeroAircraft.class.getName()).getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.get(HeroAircraft.class.getName()).getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    //子类需要这个类作为工具使用
    protected void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        if(this.heroAircraft.getHp()<0){
            g.drawString("LIFE:0", x, y);
        }else{
            g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
        }
    }
}
