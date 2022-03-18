package edu.hitsz.database;

import java.util.List;

public interface TagDao {
    String[][] getAllTags(int difficult);
    void addTag(Tag tag);
    void deleteTag(Tag tag);
}
