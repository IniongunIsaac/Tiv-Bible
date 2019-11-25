package com.iniongun.tivbible.repository.room.fontStyle

import com.iniongun.tivbible.entities.FontStyle
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

interface IFontStyleRoom {

    fun getAllFontStyles(): Observable<List<FontStyle>>

    fun getFontStyleByName(fontStyleName: String): Single<FontStyle>

    fun getFontStyleById(fontStyleId: String): Single<FontStyle>

    fun insertFontStyles(fontStyles: List<FontStyle>): Completable

    fun deleteFontStyles(fontStyles: List<FontStyle>): Completable

}