package edu.hitsz.tool;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public class Observer{
    private AbstractFlyingObject subject;
    public Observer(AbstractFlyingObject abstractAircraft){
        this.subject=abstractAircraft;
    }
    public void update(){
        subject.vanish();
    }
}
