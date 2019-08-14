package com.lrowy.dao;

import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.Favicon;

import java.util.List;

public interface BookmarkDao {
    public int saveBookmark(Bookmark bookmark);
    public List<Bookmark> findBookmark();
    public List<Bookmark> findShortcut();
    public int saveFavicon(Favicon favicon);
    public int saveBookmarkFavicon(Bookmark bookmark);
}
