package edu.hitsz.tool;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.enemy.EnemyAircraft;

import java.util.LinkedList;
import java.util.List;

public class ObserverManager{
    private List<Observer> observers=new LinkedList<>();
    public ObserverManager(){

    }
    public void addObserver(List enemyAircrafts){
        List<Observer> change=new LinkedList<>();
        for(var enemyAircraft:enemyAircrafts){
            change.add(new Observer((AbstractFlyingObject) enemyAircraft));
        }
        observers.addAll(change);
    }
    public void addObserver(Observer observer){
        observers.add(observer);
    }
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
    private void removeAll(){
        observers.clear();
    }
    public void bombAll(){
        for(Observer observer:observers){
            observer.update();
        }
        removeAll();
    }
}
