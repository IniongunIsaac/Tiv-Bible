package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.Highlight
import io.reactivex.Completable
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

    @Query("select * from Highlight where verse_id = :verseId limit 1")
    fun getHighlightById(verseId: String): Single<Highlight>

    @Query("select * from Highlight where highlighted_on = :highlightDate limit 1")
    fun getHighlightByDate(highlightDate: OffsetDateTime): Single<Highlight>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlights(vararg highlights: Highlight): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlights(highlights: List<Highlight>): Completable

    @Delete
    fun deleteHighlights(highlights: List<Highlight>): Completable

    @Query("delete from Highlight")
    fun deleteAllHighlights(): Completable

}