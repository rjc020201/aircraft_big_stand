package edu.hitsz.toolFactory;

import edu.hitsz.enemy.EnemyAircraft;
import edu.hitsz.tool.Powder;
import edu.hitsz.tool.Tool;

public class PowderFactory extends ToolFactory{
    public PowderFactory(int difficult) {
        super(difficult);
    }

    @Override
    public Tool createTool(EnemyAircraft enemyAircraft) {
        Tool tool=new Powder(enemyAircraft.getLocationX(),enemyAircraft.getLocationY(),0,5);
        return tool;
    }
}
