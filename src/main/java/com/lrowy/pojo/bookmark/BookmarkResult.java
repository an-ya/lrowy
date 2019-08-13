package com.lrowy.pojo.bookmark;

import java.util.List;

public class BookmarkResult {
    private List<Bookmark> ShortcutList;
    private List<BookmarkCategory> bookmarkCategoryList;

    public List<Bookmark> getShortcutList() {
        return ShortcutList;
    }

    public void setShortcutList(List<Bookmark> shortcutList) {
        ShortcutList = shortcutList;
    }

    public List<BookmarkCategory> getBookmarkCategoryList() {
        return bookmarkCategoryList;
    }

    public void setBookmarkCategoryList(List<BookmarkCategory> bookmarkCategoryList) {
        this.bookmarkCategoryList = bookmarkCategoryList;
    }
}
