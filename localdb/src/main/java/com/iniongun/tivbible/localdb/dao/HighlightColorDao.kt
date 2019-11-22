package com.iniongun.tivbible.localdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.HighlightColor
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface HighlightColorDao {

    @Query("select * from HighlightColor")
    fun getAllHighlightColors(): Observable<List<HighlightColor>>

    @Query("select * from HighlightColor where id = :highlightColorId limit 1")
    fun getHighlightColorById(highlightColorId: String): Single<HighlightColor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlightColors(vararg highlightColors: HighlightColor): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighlightColors(highlightColors: List<HighlightColor>): Maybe<Int>

    @Delete
    fun deleteHighlightColors(highlightColors: List<HighlightColor>): Single<Int>

}