package com.iniongun.tivbible.repository.room.book

import com.iniongun.tivbible.entities.Book
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IBookRepo {

    fun getAllBooks(): Observable<List<Book>>

    fun getBooksByTestament(testamentId: String): Observable<List<Book>>

    fun getBooksByVersion(versionId: String): Observable<List<Book>>

    fun getBookByName(bookName: String): Single<Book>

    fun getBooksByTestamentAndVersion(testamentId: String, versionId: String): Observable<List<Book>>

    fun insertBooks(books: List<Book>): Completable

    fun deleteBooks(books: List<Book>): Completable

}