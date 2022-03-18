package edu.hitsz.toolFactory;

import edu.hitsz.enemy.EnemyAircraft;
import edu.hitsz.tool.Bomb;
import edu.hitsz.tool.Tool;

public class BombFactory extends ToolFactory{

    public BombFactory(int difficult) {
        super(difficult);
    }

    @Override
    public Tool createTool(EnemyAircraft enemyAircraft) {
        Tool bomb=new Bomb(enemyAircraft.getLocationX(),enemyAircraft.getLocationY(),0,5);
        return bomb;
    }
}
