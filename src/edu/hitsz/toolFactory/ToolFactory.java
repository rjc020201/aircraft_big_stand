package edu.hitsz.toolFactory;

import edu.hitsz.enemy.EnemyAircraft;
import edu.hitsz.tool.Tool;

public abstract class ToolFactory{
    protected int difficult;
    public ToolFactory(int difficult){
        this.difficult=difficult;
    }
    public int getDifficult(){
        return difficult;
    }
    public abstract Tool createTool(EnemyAircraft enemyAircraft);
}