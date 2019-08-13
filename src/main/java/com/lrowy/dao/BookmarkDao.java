package com.lrowy.dao;

import com.lrowy.pojo.bookmark.Bookmark;
import java.util.List;

public interface BookmarkDao {
    public int save(Bookmark bookmark);
    public List<Bookmark> findBookmark();
    public List<Bookmark> findShortcut();
}
