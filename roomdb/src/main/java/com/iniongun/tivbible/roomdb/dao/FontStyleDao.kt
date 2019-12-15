package com.iniongun.tivbible.roomdb.dao

import androidx.room.*
import com.iniongun.tivbible.entities.FontStyle
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-18
 * For Tiv Bible project
 */

@Dao
interface FontStyleDao {

    @Query("select * from FontStyle")
    fun getAllFontStyles(): Observable<List<FontStyle>>

    @Query("select * from FontStyle where name like '%' || :fontStyleName || '%' limit 1")
    fun getFontStyleByName(fontStyleName: String): Single<FontStyle>

    @Query("select * from FontStyle where id = :fontStyleId")
    fun getFontStyleById(fontStyleId: String): Single<FontStyle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFontStyles(vararg fontStyles: FontStyle): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFontStyles(fontStyles: List<FontStyle>): Completable

    @Delete
    fun deleteFontStyles(fontStyles: List<FontStyle>): Completable

    @Query("delete from FontStyle")
    fun deleteAllFontStyles(): Completable

}