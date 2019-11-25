package com.iniongun.tivbible.roomdb.repositoryImpl.bookmark

import com.iniongun.tivbible.entities.Bookmark
import com.iniongun.tivbible.repository.room.bookmark.IBookmarksRoom
import com.iniongun.tivbible.roomdb.dao.BookmarkDao
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class BookmarksRoomImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
): IBookmarksRoom {

    override fun getBookmarks() = bookmarkDao.getBookmarks()

    override fun getBookmarkByDate(bookmarkedOn: String) = bookmarkDao.getBookmarkByDate(bookmarkedOn)

    override fun getBookmarkByVerse(verseId: String) = bookmarkDao.getBookmarkByVerse(verseId)

    override fun insertBookmarks(bookmarks: List<Bookmark>) = bookmarkDao.insertBookmarks(bookmarks)

    override fun deleteBookmarks(bookmarks: List<Bookmark>) = bookmarkDao.deleteBookmarks(bookmarks)
}