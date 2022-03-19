package edu.hitsz.database;

public class Tag {
    private int difficult;
    private String name;
    private int score;
    private String time;
    private int order;

    public Tag(int difficult,String name, int score, String time) {
        this.order=order;
        this.difficult = difficult;
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public int getOrder(){
        return order;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public int getDifficult() {
        return difficult;
    }
}
