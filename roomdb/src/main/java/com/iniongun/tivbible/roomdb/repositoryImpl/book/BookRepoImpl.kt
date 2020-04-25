package com.iniongun.tivbible.roomdb.repositoryImpl.book

import com.iniongun.tivbible.entities.Book
import com.iniongun.tivbible.repository.room.book.IBookRepo
import com.iniongun.tivbible.roomdb.dao.BookDao
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

class BookRepoImpl @Inject constructor(
    private val bookDao: BookDao
): IBookRepo {

    override fun getAllBooks() = bookDao.getAllBooks()

    override fun getBooksByTestament(testamentId: String) = bookDao.getBooksByTestament(testamentId)

    override fun getBooksByVersion(versionId: String) = bookDao.getBooksByVersion(versionId)

    override fun getBookByName(bookName: String) = bookDao.getBookByName(bookName)

    override fun getBooksByTestamentAndVersion(
        testamentId: String,
        versionId: String
    ) = bookDao.getBooksByTestamentAndVersion(testamentId, versionId)

    override fun insertBooks(books: List<Book>) = bookDao.insertBooks(books)

    override fun deleteBooks(books: List<Book>) = bookDao.deleteBooks(books)
}