package edu.hitsz.application;
import edu.hitsz.database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextJPanel extends JPanel {
    private static final String[] colum=new String[]{"名次","名字","得分","记录的时间"};
    private JButton jb1=new JButton("不服，删除记录");
    private JButton jb2=new JButton("不服，再来一局");
    private JLabel jlHead;
    JScrollPane tableContainer;
    private TagDao tagDao;
    private int difficult;
    public NextJPanel(int difficult){
        this.difficult=difficult;
        init();
    }

    public void InitRemove(){
        this.remove(jlHead);
        this.remove(jb2);
        this.remove(jb1);
        this.remove(tableContainer);
    }

    public void init(){
        //自行选择实现持久层
        this.tagDao= TagDaoImplementByFile.getInstance();
        String[][] ans= getData();
        JTable table=new JTable(ans,colum);
        tableContainer=new JScrollPane(table);
        jb1=new JButton("不服，删除记录");
        jb2=new JButton("不服，再来一局");
        jlHead=new JLabel("难度是:"+difficult);
        jlHead.setPreferredSize(new Dimension(400,30));

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count=table.getSelectedRow();
                String getname1=table.getValueAt(count,0).toString();
                String getname2=table.getValueAt(count,1).toString();
                String getname3=table.getValueAt(count,2).toString();
                String getname4=table.getValueAt(count,3).toString();
                deleteData(new Tag(difficult,getname2,Integer.valueOf(getname3),getname4));
                InitRemove();
                init();
                JFrameMain.getInstance().revalidate();
            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMain.getInstance().changePreJPanel();
            }
        });
        this.add(jlHead);
        this.add(tableContainer,BorderLayout.CENTER);
        this.add(jb1);
        this.add(jb2);
    }

    public String[][] getData(){
//        return sortData(MySQLConect.getInstance().searchAll(difficult));
        return sortData(tagDao.getAllTags(difficult));
    }

    public String[][] sortData(String[][] str){
        int len=str.length;
        int[] sortArr=new int[len];
        for(int i=0;i<len;i++){
            sortArr[i]=Integer.valueOf(str[i][1]);
        }

        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){
                if(sortArr[j+1]>sortArr[j]){
                    int temp=sortArr[j+1];
                    sortArr[j+1]=sortArr[j];
                    sortArr[j]=temp;
                    String[] tempStr=str[j+1];
                    str[j+1]=str[j];
                    str[j]=tempStr;
                }
            }
        }

        for(int i=0;i<len;i++){
            str[i]=changeString(str[i],i+1);
        }
        return str;
    }

    public String[] changeString(String[] str,int order){
        int len=str.length;
        String[] newStr=new String[len+1];
        newStr[0]=order+"";
        for(int i=0;i<len;i++){
            newStr[i+1]=str[i];
        }
        return newStr;
    }

    public void deleteData(Tag tag){
        tagDao.deleteTag(tag);
    }
}