package edu.hitsz.application;


import edu.hitsz.enemy.Boss;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.enemy.InfernalEnemy;
import edu.hitsz.enemy.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.tool.Bomb;
import edu.hitsz.tool.Drug;
import edu.hitsz.tool.Powder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();
    public static BufferedImage BACKGROUND_IMAGE;
    public static int bgp;

    static {
        bindImage(HeroAircraft.class.getName(),"src/images/hero.png");
        bindImage(MobEnemy.class.getName(),"src/images/mob.png");
        bindImage(InfernalEnemy.class.getName(),"src/images/elite.png");
        bindImage(Boss.class.getName(),"src/images/boss.png");
        bindImage(HeroBullet.class.getName(),"src/images/bullet_hero.png");
        bindImage(EnemyBullet.class.getName(),"src/images/bullet_enemy.png");
        bindImage(Drug.class.getName(),"src/images/prop_blood.png");
        bindImage(Powder.class.getName(),"src/images/prop_bullet.png");
        bindImage(Bomb.class.getName(),"src/images/prop_bomb.png");
        setBackgroundImage("src/images/bg.jpg");
    }

    public static void bindImage(String name,String path){
        try {
            CLASSNAME_IMAGE_MAP.put(name,ImageIO.read(new FileInputStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setBackgroundImage(String path){
        try {
            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream(path));
            if(path.equals("src/images/bg2.jpg")){
                bgp=2;
            }else if(path.equals("src/images/bg3.jpg")){
                bgp=3;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
