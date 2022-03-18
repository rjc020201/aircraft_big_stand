package edu.hitsz.database;
import java.io.*;

public class TagDaoImplementByFile implements TagDao{
    private File easyFile=new File("src/dataFile/easy.txt");
    private File commonFile=new File("src/dataFile/common.txt");
    private File difficultFile=new File("src/dataFile/difficult.txt");
    private boolean startEasy;
    private boolean startCommon;
    private boolean startDifficult;

    private static TagDaoImplementByFile tagDaoImplementByFile=new TagDaoImplementByFile();

    private TagDaoImplementByFile(){
        initFile(this.easyFile);
        initFile(this.commonFile);
        initFile(this.difficultFile);
        this.startEasy=getStart(this.easyFile);
        this.startCommon=getStart(this.commonFile);
        this.startDifficult=getStart(this.commonFile);
    }

    private boolean getStart(File file){
        BufferedReader br= null;
        String str=null;
        try {
            br = new BufferedReader(new FileReader(file));
            str=br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(str==null || str.equals(""))
            return true;
        return false;
    }


    private void appendFile(String str,File file){
        try {
            BufferedWriter fw=new BufferedWriter(new FileWriter(file,true));
            if(getStart(file)){
                fw.write(str);
            }else{
                fw.write("\n");
                fw.write(str);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static TagDaoImplementByFile getInstance() {
        return tagDaoImplementByFile;
    }

    public void initFile(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
//                file.delete();
                System.out.println(file.getName()+"产生了");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String[][] getAllTags(int difficult) {
        File file=easyFile;
        switch (difficult){
            case 1:
                file =easyFile;
                break;
            case 2:
                file =commonFile;
                break;
            case 3:
                file =difficultFile;
                break;
            default:;
        }
        String[][] ans=null;
        BufferedReader br;
        try {
            br=new BufferedReader(new FileReader(file));
            String line;
            String[] element;
            int i=0;
            while((line=br.readLine())!=null){
                i++;
            }
            br.close();

            ans=new String[i][];
            br=new BufferedReader(new FileReader(file));
            i=0;
            while((line=br.readLine())!=null){
                element=line.split(" ");
                ans[i]=element;
                i++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    @Override
    public void addTag(Tag tag) {
        String str=tag.getName()+" "+tag.getScore()+" "+tag.getTime();
        switch (tag.getDifficult()){
            case 1:
                appendFile(str,easyFile);
                break;
            case 2:
                appendFile(str,commonFile);
                break;
            case 3:
                appendFile(str,difficultFile);
                break;
            default:;
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        File tmpFile=new File("src/dataFile/tmpFile.txt");
        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File targetFile=null;
//        this.difficultFile.delete();
        switch (tag.getDifficult()){
            case 1:
                targetFile=easyFile;
                break;
            case 2:
                targetFile=commonFile;
                break;
            case 3:
                targetFile=difficultFile;
                break;
            default:;
        }
//        targetFile.delete();
        String str=tag.getName()+" "+tag.getScore()+" "+tag.getTime();
        try {
            BufferedWriter tmpbw=new BufferedWriter(new FileWriter(tmpFile));
            BufferedReader targetbw=null;
            if(targetFile!=null){
                targetbw=new BufferedReader(new FileReader(targetFile));
            }else{
                System.out.println("targetFile is null!out");
                System.exit(-1);
            }
            String line;
            int i=0;
            while((line=targetbw.readLine())!=null){
                if(line.equals(str)){
                    continue;
                }
                if(i==0){
                    tmpbw.write(line);
                    i++;
                }else{
                    tmpbw.write("\n");
                    tmpbw.write(line);
                }
            }
            tmpbw.flush();
            tmpbw.close();
            targetbw.close();
//            if(targetFile.exists()){
//                System.out.println("文件活着");
//            }
//            if(targetFile.delete()){
//                System.out.println("删除了文件");
//            }
            targetFile.delete();
            tmpFile.renameTo(targetFile);
//            tmpFile.delete();
//            System.out.println("重新命名了这个文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        TagDaoImplementByFile tagDaoImplementByFile=TagDaoImplementByFile.getInstance();
//        tagDaoImplementByFile.deleteTag(new Tag(1,"飞洒",0,"1s"));
//        tagDaoImplementByFile.easyFile.delete();
    }
}
