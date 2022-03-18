package edu.hitsz.tool;

import edu.hitsz.application.Game;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.AbstractBullet;

import java.util.LinkedList;

public class Bomb extends Tool{
    ObserverManager observerManager= Game.observerManager;
    public Bomb(){

    }
    public Bomb(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);
    }

    @Override
    public void vanish() {
        super.vanish();
        observerManager.bombAll();
    }
}
