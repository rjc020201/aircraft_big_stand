package edu.hitsz.toolFactory;

import edu.hitsz.enemy.EnemyAircraft;
import edu.hitsz.tool.Drug;
import edu.hitsz.tool.Tool;

public class DrugFactory extends ToolFactory{
    public DrugFactory(int difficult) {
        super(difficult);
    }

    @Override
    public Tool createTool(EnemyAircraft enemyAircraft) {
        Tool tool=new Drug(enemyAircraft.getLocationX(),enemyAircraft.getLocationY(),0,10,1);
        return tool;
    }
}
