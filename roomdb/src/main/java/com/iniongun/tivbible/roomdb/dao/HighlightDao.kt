package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Highlight
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.threeten.bp.OffsetDateTime

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface HighlightDao {

    @Query("select * from Highlight")
    fun getAllHighlights(): Observable<List<Highlight>>

    @Query("select * from Highlight where id = :highlightId limit 1")
    fun getHighlightById(highlightId: String): Single<Highlight>

    @Query("select * from Highlight where highlighted_on = :highlightDate limit 1")
    fun getHighlightByDate(highlightDate: OffsetDateTime): Single<Highlight>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlights(vararg highlights: Highlight): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlights(highlights: List<Highlight>): Maybe<Int>

    @Delete
    fun deleteHighlights(highlightColors: List<Highlight>): Single<Int>

}