package com.lrowy.dao;

import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.BookmarkCategory;
import com.lrowy.pojo.bookmark.Favicon;

import java.util.List;

public interface BookmarkDao {
    public int saveBookmark(Bookmark bookmark);
    public List<Bookmark> findBookmark();
    public List<Bookmark> findShortcut();
    public int updateBookmark(Bookmark bookmark);
    public int deleteBookmark(int bookmarkId);
    public int saveFavicon(Favicon favicon);
    public Favicon findFaviconByDomain(String domain);
    public Favicon findFaviconByBookmarkId(int bookmarkId);
    public int deleteFavicon(int faviconId);
    public int saveBookmarkFavicon(Bookmark bookmark);
    public int deleteBookmarkFavicon(int bookmarkId);
    public int save(BookmarkCategory bookmarkCategory);
    public List<BookmarkCategory> findBookmarkCategory();
}
