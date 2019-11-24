package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Book
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface BookDao {

    @Query("select * from Book order by order_no asc")
    fun getAllBooks(): Observable<List<Book>>

    @Query("select * from Book where testament_id = :testamentId order by order_no asc")
    fun getBooksByTestament(testamentId: String): Observable<List<Book>>

    @Query("select * from Book where version_id = :versionId order by order_no asc")
    fun getBooksByVersion(versionId: String): Observable<List<Book>>

    @Query("select * from Book where testament_id = :testamentId and version_id = :versionId order by order_no asc")
    fun getBooksByTestamentAndVersion(testamentId: String, versionId: String): Observable<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<Book>): Maybe<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg books: Book): Completable

    @Delete
    fun deleteBooks(books: List<Book>): Single<Int>

}