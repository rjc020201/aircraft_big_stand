package edu.hitsz.application;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        PreJPanel game=new PreJPanel();
        JFrameMain.getInstance().changePreJPanel();
        JFrameMain.getInstance().setVisible(true);
    }
}
