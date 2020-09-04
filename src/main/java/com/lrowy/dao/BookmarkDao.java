package com.lrowy.dao;

import com.lrowy.pojo.bookmark.Bookmark;
import com.lrowy.pojo.bookmark.BookmarkCategory;

import java.util.List;

public interface BookmarkDao {
    public int saveBookmark(Bookmark bookmark);
    public List<Bookmark> findBookmark();
    public Bookmark findBookmarkByBookmarkId(int bookmarkId);
    public int updateBookmark(Bookmark bookmark);
    public int deleteBookmark(int bookmarkId);
    public int saveBookmarkCategory(BookmarkCategory bookmarkCategory);
    public List<BookmarkCategory> findBookmarkCategory();
}
