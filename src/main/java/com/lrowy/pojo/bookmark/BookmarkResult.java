package com.lrowy.pojo.bookmark;

import java.util.List;

public class BookmarkResult {
    private List<Bookmark> shortcuts;
    private List<Bookmark> bookmarks;
    private List<BookmarkCategory> bookmarkCategories;

    public BookmarkResult() {

    }

    public List<Bookmark> getShortcuts() {
        return shortcuts;
    }

    public void setShortcuts(List<Bookmark> shortcuts) {
        this.shortcuts = shortcuts;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<BookmarkCategory> getBookmarkCategories() {
        return bookmarkCategories;
    }

    public void setBookmarkCategories(List<BookmarkCategory> bookmarkCategories) {
        this.bookmarkCategories = bookmarkCategories;
    }
}
