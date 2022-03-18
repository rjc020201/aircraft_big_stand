package edu.hitsz.tool;

import edu.hitsz.basic.AbstractFlyingObject;

public class Drug extends Tool{
    private int hp;
    public Drug(){

    }
    public Drug(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX,locationY,speedX,speedY);
        this.hp=hp;
    }
    public int getHp(){
        return hp;
    }
}
