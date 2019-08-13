package com.lrowy.dao;

import com.lrowy.pojo.bookmark.BookmarkCategory;

import java.util.List;

public interface BookmarkCategoryDao {
    public int save(BookmarkCategory bookmarkCategory);
    public List<BookmarkCategory> findBookmarkCategory();
}
