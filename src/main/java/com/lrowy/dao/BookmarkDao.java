package com.lrowy.dao;

import com.lrowy.pojo.Bookmark;
import java.util.List;

public interface BookmarkDao {
    public int save(Bookmark bookmark);
    public List<Bookmark> findBookmark();
}
