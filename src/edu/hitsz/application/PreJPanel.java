package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreJPanel extends JPanel {
    private JButton easy;
    private JButton common;
    private JButton difficult;
    private JButton music;
    private JComboBox jList;
    private Image image=new ImageIcon("src/images/pre.png").getImage();
    private boolean musicOpen=false;
    private static Dimension buttonSize=new Dimension(200,50);

    PreJPanel(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER,200,90));
        init();
    }

    private void init(){
        easy=new JButton("简单");
        common=new JButton("普通");
        difficult=new JButton("困难");
        easy.setPreferredSize(buttonSize);
        common.setPreferredSize(buttonSize);
        difficult.setPreferredSize(buttonSize);



        jList=new JComboBox(new String[]{"音乐:开", "音乐:关"});
        jList.setPreferredSize(new Dimension(100,45));

        this.add(easy);
        this.add(common);
        this.add(difficult);
        this.add(jList);

        addListen(easy,1);
        addListen(common,2);
        addListen(difficult,3);
    }

    protected void paintComponent(Graphics g){
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }

    private void addListen(JButton j,int number){
        j.addActionListener(e -> JFrameMain.getInstance().changeGameJPanel(number,jList.getSelectedIndex()==0));
    }
}
