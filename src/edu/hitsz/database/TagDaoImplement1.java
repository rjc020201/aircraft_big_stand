package edu.hitsz.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TagDaoImplement1 implements TagDao{

    List<Tag> easyTags=new ArrayList<>();
    List<Tag> commonTags=new ArrayList<>();
    List<Tag> difficultTags=new ArrayList<>();
    private static TagDaoImplement1 tagDaoImplement1=new TagDaoImplement1();

    private TagDaoImplement1() {
        Tag tag1=new Tag(1,"小明",100,"1h30min");
        Tag tag2=new Tag(2,"小红",100,"1h30min");
        Tag tag3=new Tag(3,"沸羊羊",100,"1h30min");
        easyTags.add(tag1);
        commonTags.add(tag2);
        difficultTags.add(tag3);
    }

    public static TagDaoImplement1 getInstance(){
        return tagDaoImplement1;
    }

    @Override
    public String[][] getAllTags(int difficult) {
        List<Tag> choose=new LinkedList<>();
        switch (difficult){
            case 1:
                choose=easyTags;
                break;
            case 2:
                choose=commonTags;
                break;
            case 3:
                choose=difficultTags;
                break;
            default:;
        }
        String[][] ans=new String[choose.size()][];
        int i=0;
        for(Tag tag:choose){
            String[] tmp=new String[]{tag.getName(),""+tag.getScore(),tag.getTime()};
            ans[i]=tmp;
            i++;
        }
        return ans;
    }

    @Override
    public void addTag(Tag tag) {
        switch (tag.getDifficult()){
            case 1:
                easyTags.add(tag);
                break;
            case 2:
                commonTags.add(tag);
                break;
            case 3:
                difficultTags.add(tag);
                break;
            default:;
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        Tag searchTag=null;
        switch (tag.getDifficult()){
            case 1:
                searchTag=searchTag(tag,easyTags);
                easyTags.remove(searchTag);
                break;
            case 2:
                searchTag=searchTag(tag,easyTags);
                commonTags.remove(searchTag);
                break;
            case 3:
                searchTag=searchTag(tag,easyTags);
                difficultTags.remove(searchTag);
                break;
            default:;
        }
    }

    private Tag searchTag(Tag tag,List<Tag> easyTags){
        Tag tagAns=null;
        for(Tag tag1:easyTags){
            if(tag1.getName().equals(tag.getName()) && tag1.getScore()==tag.getScore() && tag.getTime().equals(tag1.getTime())){
                tagAns=tag1;
                break;
            }
        }
        return  tagAns;
    }
}
