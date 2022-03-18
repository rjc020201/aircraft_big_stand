package edu.hitsz.application;
import edu.hitsz.database.*;

import javax.swing.*;
import java.awt.*;

import static edu.hitsz.application.Main.WINDOW_HEIGHT;
import static edu.hitsz.application.Main.WINDOW_WIDTH;

public class JFrameMain extends JFrame{
    private static JFrameMain jFrameMain=new JFrameMain("Aircraft War");
    public JPanel preJPanel=null;

    static {
        initFrame();
    }

    private JFrameMain(String str){
        super(str);
    }


    public static JFrameMain getInstance(){
        return jFrameMain;
    }

    private static void initFrame(){
        System.out.println("Hello Aircraft War");
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrameMain.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrameMain.setResizable(false);
        //设置窗口的大小和位置,居中放置
        jFrameMain.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changePreJPanel(){
        JPanel newJPanel=new PreJPanel();
        addJPanel(newJPanel);
    }

    public void changeGameJPanel(int easyNum, boolean musicOpen){
        Game newJPanel=new EasyGame(easyNum,musicOpen);
        switch (easyNum){
            case 1:
                newJPanel=new EasyGame(easyNum,musicOpen);break;
            case 2:
                newJPanel=new CommonGame(easyNum,musicOpen);break;
            case 3:
                newJPanel=new DifficultGame(easyNum,musicOpen);break;
            default:;
        }
        System.out.println(ImageManager.bgp);
        addJPanel(newJPanel);
        newJPanel.action();
    }

    //下面的两个方法重载是无奈之举
    public void changeNextJPanel(String score, String time, int difficult){
        JPanel newJPanel=new NextJPanel(difficult);
        addJPanel(newJPanel);
        PopUpWindow(score,time,difficult);
    }

    public void changeNextJPanel(int difficult){
        JPanel newJPanel=new NextJPanel(difficult);
        addJPanel(newJPanel);
    }

    public void PopUpWindow(String score,String time,int difficult){
        String input=JOptionPane.showInputDialog("输入你的名字让大家看看你傲人的战绩吧!!!");
        TagDao tagDao= TagDaoImplementByFile.getInstance();
        if(input == null) {

        }else{
            tagDao.addTag(new Tag(difficult,input,Integer.valueOf(score),time));
            changeNextJPanel(difficult);
        }
    }

    private void addJPanel(JPanel newJPanel){
        if(preJPanel==null) {
            this.preJPanel=newJPanel;
            jFrameMain.add(newJPanel);
        }else{
            jFrameMain.remove(preJPanel);
            jFrameMain.add(newJPanel);
            jFrameMain.revalidate();
            jFrameMain.repaint();
            preJPanel=newJPanel;
        }
    }
}
